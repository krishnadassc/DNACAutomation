package com.cisco.dnac.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cisco.dnac.scheduler.dao.RemoteTaskRunInfo;
import com.cisco.dnac.scheduler.dao.SystemStateEntry;
import com.cisco.dnac.scheduler.db.provider.DNASchedulerDBUtil;
import com.cisco.dnac.scheduler.dto.ScheduleTask;

public final class SystemTaskScheduler {
	private static final Logger logger = Logger.getLogger(SystemTaskScheduler.class);
	private static final String TASK_PREFIX = "task.";
	private static final int SYSTEMTASK_WORKER_THREAD_OVERHEAD_PERCENT = 10;
	private static final int SCHEDULER_CORE_POOL_SIZE = 10;
	private boolean initialized = false;
	private boolean isServiceNode = false;
	private SystemTaskRegistry registry = SystemTaskRegistry.getInstance();
	private Map<String, MyScheduledExecutorService> taskExecutorServiceMap = new HashMap<String, MyScheduledExecutorService>(); // Map<taskName,
																																// MyScheduledExecutorService>
	private List<MyScheduledExecutorService> executorServices = new ArrayList<MyScheduledExecutorService>();
	private int corePoolSize = 25;
	private int workerThreadOverheadPercent = 40;
	private int numOfExecutorServices = 0;
	private static SystemTaskScheduler instance = null;

	@Autowired
	private SystemTaskStatusProvider provider;

	@Autowired
	private DNASchedulerDBUtil dbutil;

	private SystemTaskScheduler() {

		try {
			int configuredCorePoolSize = SCHEDULER_CORE_POOL_SIZE;
			if (configuredCorePoolSize > 0) {
				this.corePoolSize = configuredCorePoolSize;
			}
		} catch (NumberFormatException nfe) {
			logger.warn("Failed to parse " + SCHEDULER_CORE_POOL_SIZE + " from properties");
		}
		try {
			int configuredWorkerThreadOverheadPercent = SYSTEMTASK_WORKER_THREAD_OVERHEAD_PERCENT;
			if (configuredWorkerThreadOverheadPercent > 0) {
				this.workerThreadOverheadPercent = configuredWorkerThreadOverheadPercent;
			}
		} catch (NumberFormatException nfe) {
			logger.warn("Failed to parse " + SYSTEMTASK_WORKER_THREAD_OVERHEAD_PERCENT + " from properties");
		}
	}

	public static SystemTaskScheduler getInstance() {
		if (instance == null) {
			instance = new SystemTaskScheduler();

		}
		return instance;
	}

	public void addNonRecurringTask(SystemTaskEntry entry) {
		logger.info("Registering Non-recurring Schedule task: " + entry.getTask().getTaskName());
		registry.addTask(entry);
		provider.setOK(entry.getTask().getTaskName(), entry.getCategory(), entry.getLabel(), entry.getDescription());
	}

	public void addTask(SystemTaskEntry entry) {
		logger.info("Registering Schedule task: " + entry.getTask().getTaskName());
		registry.addTask(entry);
		if (!initialized) {
			logger.debug("System not initialized, can not schedule any task now");
			return;
		}
		provider.setPrepare(entry.getTask().getTaskName(), entry.getCategory(), entry.getLabel(),
				entry.getDescription());
		schedule(entry.getTask(), 0L, getTaskFrequency(entry.getTask()));
	}

	public void addTask(SystemTaskEntry entry, long initialDelay) {
		logger.info(
				"Registering Schedule task: " + entry.getTask().getTaskName() + "with initial Delay = " + initialDelay);
		registry.addTask(entry);
		long frequency = -1;
		if (!initialized) {
			logger.debug("System not initialized, can not schedule any task now");
			return;
		}
		provider.setPrepare(entry.getTask().getTaskName(), entry.getCategory(), entry.getLabel(),
				entry.getDescription());
		frequency = getTaskFrequency(entry.getTask());
		schedule(entry.getTask(), initialDelay, frequency);
	}

	private void schedule(ScheduleTask task, long initialDelay, long frequency) {
		if (isServiceNode) {
			logger.info("Task not scheduled as node is Service Node: " + task.getTaskName());
			return;
		}

		logger.info("Scheduling Task: " + task.getTaskName() + " with initial delay(msec): " + initialDelay
				+ " and with frequency(msec): " + frequency);
		provider.setInQueue(task.getTaskName(), initialDelay);

		MyScheduledExecutorService executorService = getExecutorService(task);
		SystemTaskExecutor systemTaskExecutor = new SystemTaskExecutor(task);
		executorService.scheduleAtFixedRate(systemTaskExecutor, initialDelay, frequency, TimeUnit.MILLISECONDS);
	}

	private MyScheduledExecutorService getExecutorService(ScheduleTask task) {
		MyScheduledExecutorService executorService = this.taskExecutorServiceMap.get(task.getTaskName());
		if (executorService == null) {
			logger.info("Task '" + task.getTaskName() + "' not found in the SystemTaskScheduler");
			executorService = addTaskToExecutorService();
			this.taskExecutorServiceMap.put(task.getTaskName(), executorService);
		}
		return executorService;
	}

	private MyScheduledExecutorService addTaskToExecutorService() {
		Collections.sort(executorServices);
		MyScheduledExecutorService execSvc = executorServices.get(0);
		int noOfExecutors = execSvc.getTotalTaskExecutors();
		if (noOfExecutors < (int) (corePoolSize * (1 + (float) workerThreadOverheadPercent / 100))) {
			return execSvc;
		}
		logger.info("No ExecutorService available for scheduling any more task. Creating a new ExecutorService...");
		MyScheduledExecutorService executorService = createExecutorService();
		this.executorServices.add(executorService);
		return executorService;
	}

	public void scheduleNow(String taskName) {
		ScheduleTask task = SystemTaskRegistry.getInstance().getTaskByName(taskName);
		if (task == null) {
			logger.warn("Task not available: " + taskName);
			return;
		}
		scheduleNow(task);

	}

	public void scheduleNow(final ScheduleTask task) {
		logger.info("Scheduling run now task");
		if (task != null) {
			logger.info("Scheduling run now task : " + task.getTaskName());
		}

		Thread runNow = new Thread() {

			public void run() {
				new SystemTaskExecutor(task, true).run();
			}
		};
		runNow.start();

		logger.info("Task scheduled to execute immediately");
	}

	public void init() {
		if (isServiceNode) {
			logger.info("System Scheduler is not initialized as current node is Service Node");
			return;
		}

		waitForSystemSteadyState();

		logger.info("Initializing System Task Scheduler");
		initialized = true;

		logger.info("Setting incomplete task status to 'Cancelled'");
		dbutil.cleanupTaskStatus();

		Collection<SystemTaskEntry> entries = registry.getAllTasks();

		logger.info("Scheduler has " + entries.size() + " tasks to schedule");

		createExecutorServices();

		for (SystemTaskEntry entry : registry.getAllTasks()) {

			ScheduleTask task = entry.getTask();
			if (task != null && task.getFrequency() == ScheduleTask.FREQUENCY_NON_RECURRING) {
				continue;
			}
			logger.info("Preparing for scheduling task: " + entry.getTask().getTaskName());
			provider.setPrepare(entry.getTask().getTaskName(), entry.getCategory(), entry.getLabel(),
					entry.getDescription());
			schedule(entry.getTask());

		}
		logger.info("All System tasks scheduled for periodic execution");
	}

	private void createExecutorServices() {
		numOfExecutorServices = (int) Math.ceil(registry.getAllTasks().size() / this.corePoolSize);
		for (int i = 0; i <= numOfExecutorServices; i++) {
			String executorServiceName = "SystemTaskSheduler[" + corePoolSize * i + "-" + ((corePoolSize * (i + 1)) - 1)
					+ "]";
			MyScheduledExecutorService executorService = createExecutorService(executorServiceName);
			executorServices.add(executorService);
		}
	}

	private MyScheduledExecutorService createExecutorService(String executorServiceName) {
		MyScheduledExecutorService executorService = new MyScheduledExecutorService(executorServiceName, corePoolSize);
		return executorService;
	}

	private MyScheduledExecutorService createExecutorService() {
		String executorServiceName = "SystemTaskSheduler[" + corePoolSize * numOfExecutorServices + "-"
				+ ((corePoolSize * ++numOfExecutorServices) - 1) + "]";
		return this.createExecutorService(executorServiceName);
	}

	private void schedule(ScheduleTask task) {
		long frequency = getTaskFrequency(task);
		long initialDelay = getRandomStartDelay(frequency);
		logger.info("Scheduling task " + frequency + ", " + initialDelay);
		schedule(task, initialDelay, frequency);
	}

	private long getRandomStartDelay(long freq) {
		RandomScheduleDelay delay = null;

		// to disable random sleep, just return -1L;
		if (freq <= (15 * 60 * 1000L)) // 15 minute task
		{
			delay = new RandomScheduleDelay(2, 1, 15); // start with initial delay=5 mins,
														// min-spread=1minute, max-spread-15mins
		} else if (freq <= (60 * 60 * 1000L)) // Hourly task
		{
			delay = new RandomScheduleDelay(2, 5, 60); // start with initial delay=15mins,
														// min-spread=5 mins, max-spread=60mins
		} else if (freq <= (4 * 60 * 60 * 1000L)) // 4-hour task
		{
			delay = new RandomScheduleDelay(5, 5, 4 * 60); // start with initial delay=15 mins,
															// min-spread=5mins, max-spread=4hours
		} else {
			delay = new RandomScheduleDelay(60, 15, 24 * 60); // start with initial delay=1hour,
																// min-spread=15 mins, max-spread=24
																// hours
		}
		logger.info("Getting random delay for frequency " + freq + " as " + delay.getDelay());
		return delay.getDelay();

	}

	private long getTaskFrequency(ScheduleTask task) {
		long freq = 0L;

		SystemStateEntry entry = dbutil.getStatus(task.getTaskName());
		if (entry == null || entry.getFrequency() <= 0L) {
			freq = task.getFrequency();
		} else {
			freq = entry.getFrequency();
		}
		logger.info("Getting the frequency for task: " + task.getTaskName() + " as " + freq);
		return freq;
	}

	private void waitForSystemSteadyState() {
		logger.info("Waiting for system steady state behaviour");
		int nofSecs = 1000;
		try {
			Thread.sleep(nofSecs * 1000L);
		} catch (Throwable ex) {
			logger.error("sleep interrupted", ex);
		}
	}

	public void removeTaskByPattern(String patternString) {
		Pattern pattern = Pattern.compile(".+\\s+-\\s+" + patternString + "$");
		Collection<SystemTaskEntry> tasks = SystemTaskRegistry.getInstance().getAllTasks();
		List<SystemTaskEntry> taskList = new ArrayList<SystemTaskEntry>();
		taskList.addAll(tasks);
		for (SystemTaskEntry task : taskList) {
			if (task != null) {
				String taskName = task.getLabel();
				logger.debug("taskName [" + taskName + "]");
				Matcher matcher = pattern.matcher(taskName);
				if (matcher.find()) {
					logger.info("Deleting Schedule Task " + taskName);
					removeTask(taskName);
				}
			}
		}
	}

	private void cancelTask(String taskName) {
		MyScheduledExecutorService executorService = this.taskExecutorServiceMap.get(taskName);
		if (executorService == null) {
			logger.warn("Task '" + taskName + "' not found in SystemTaskScheduler");
			return;
		}
		executorService.removeTaskExecutor(taskName);
		this.taskExecutorServiceMap.remove(taskName);
		logger.info("Cancelled task " + taskName);
	}

	public void removeTask(String taskName) {
		cancelTask(taskName);
		SystemTaskRegistry.getInstance().removeTask(taskName);
		provider.deleteTask(taskName);
		logger.info("Task " + taskName + " successfully removed from the system");
	}

	public void modifyTask(SystemStateEntry entry) {

		String taskName = entry.getProperty();
		taskName = taskName.substring(taskName.indexOf(".") + 1);

		ScheduleTask task = registry.getTaskByName(taskName);
		long currFrequency = getTaskFrequency(task);
		dbutil.updateSystemStateProperty(entry);

		if (entry.getFrequency() != currFrequency) {
			logger.info("Frequency for task " + taskName + " is changed to " + entry.getFrequency());
			cancelTask(taskName);
			if (!entry.isDisabled()) {
				schedule(task);
			}
		}
	}

	/*public void resetTaskStatusOnAgentDown(String agentName, int nodeId) {
		List<SystemStateEntry> status = dbutil.getTasksByAgentAndStatus(agentName,
				SystemTaskStatusProvider.STATUS_IN_PROGRESS);

		if (status != null) {
			logger.info(
					"Setting task status for " + status.size() + " tasks  to ERROR as agent is down : " + agentName);
			for (SystemStateEntry entry : status) {
				dbutil.updateSystemStateProperty(entry.getProperty(), SystemTaskStatusProvider.STATUS_ERROR);

				RemoteTaskRunInfo runInfo = new RemoteTaskRunInfo();
				runInfo.setStatus(SystemTaskStatusProvider.STATUS_ERROR);
				runInfo.setStartTime(entry.getStartTime());
				runInfo.setEndTime(System.currentTimeMillis());
				long duration = runInfo.getEndTime() - runInfo.getStartTime();
				runInfo.setDuration(duration);
				runInfo.setDurationInSecs((int) (duration / 1000));
				runInfo.setTimestamp(System.currentTimeMillis());
				runInfo.setAgentLabel(agentName);
				String taskName = entry.getProperty();

				if (taskName != null && taskName.indexOf(TASK_PREFIX) == 0) {
					taskName = taskName.substring(TASK_PREFIX.length());
				}
				runInfo.setTaskName(taskName);
				dbutil.archiveStatus(runInfo);
			}
		}
	}*/
}
