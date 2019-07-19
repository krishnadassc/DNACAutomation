package com.cisco.dnac.scheduler;

import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cisco.dnac.scheduler.dao.RemoteTaskRunInfo;
import com.cisco.dnac.scheduler.db.provider.DNASchedulerDBUtil;
import com.cisco.dnac.scheduler.dto.SchedulerConstants;
import com.cisco.dnac.scheduler.dto.ScheduleTask;

public class SystemTaskExecutor extends TimerTask implements Runnable {

	private static final Logger logger = Logger.getLogger(SystemTaskExecutor.class);

	private ScheduleTask task;
	@Autowired
	private RemoteTaskRunInfo runInfo;

	@Autowired
	private SystemTaskStatusProvider provider;

	@Autowired
	private DNASchedulerDBUtil dbutil;

	private long lastExecuted = 0L;

	boolean isThruRunNow = false;

	public SystemTaskExecutor(ScheduleTask task) {
		super();
		this.task = task;
	}

	public SystemTaskExecutor(ScheduleTask task, boolean isThruRunNow) {
		super();
		this.task = task;
		this.isThruRunNow = isThruRunNow;
	}

	@Override
	public void run() {
		int priority = getTaskPriority(task);
		logger.debug("Preparing to execute task: " + task.getTaskName() + "; frequency="
				+ task.getFrequency() / 1000 / 60 + " minutes; Priority=" + priority);
		Thread.currentThread().setPriority(priority);

		if (provider.isTaskAlreadyRunning(task.getTaskName())) {
			logger.info("Task " + task.getTaskName() + " is already in queue or running, skipping this cycle");
			return;
		}

		if (!isThruRunNow && dbutil.isTaskDisabled(task.getTaskName())) {
			logger.info("Task " + task.getTaskName() + " is disabled");
			return;
		}

		execute();
	}

	public String getTaskName() {
		return task.getTaskName();
	}

	private long getLasExecTimestamp()

	{

		return dbutil.getStatus(task.getTaskName()).getEndTime();

	}

	public void execute() {
		provider.updateNodeInfo(task.getTaskName(),
				// ClusterLeafConstants.LOCAL_HOST_IP,
				SchedulerConstants.LOCAL_HOST_NAME, SchedulerConstants.LOCAL_HOST_NAME);
		logger.info("Executing task locally: " + task.getTaskName());
		long startTime = System.currentTimeMillis();
		long endTime = -1;

		String errMsg = null;
		boolean isOK = false;

		try {

			provider.setInProgress(task.getTaskName());
			runInfo.setStartTime(startTime);
			runInfo.setStatus(SystemTaskStatusProvider.STATUS_IN_PROGRESS);

			logger.debug("Start executing task. name=" + task.getTaskName() + "; status="
					+ SystemTaskStatusProvider.STATUS_OK + "; lastExecuted=" + this.lastExecuted);
			task.execute(getLasExecTimestamp());
			this.lastExecuted = System.currentTimeMillis();
			logger.debug("Done executing task. name=" + task.getTaskName() + "; status="
					+ SystemTaskStatusProvider.STATUS_OK + "; lastExecuted=" + this.lastExecuted);
			isOK = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
		} finally {
			if (isOK) {
				provider.setOK(task.getTaskName());
				runInfo.setStatus(SystemTaskStatusProvider.STATUS_OK);
			} else {
				provider.setError(task.getTaskName(), errMsg);
				runInfo.setStatus(errMsg);
			}
			endTime = System.currentTimeMillis();
			runInfo.setEndTime(endTime);
			long duration = runInfo.getEndTime() - runInfo.getStartTime();
			runInfo.setDuration(duration);
			runInfo.setDurationInSecs((int) (duration / 1000));

			runInfo.setTimestamp(System.currentTimeMillis());

			runInfo.setAgentLabel(SchedulerConstants.LOCAL_HOST_NAME);
			runInfo.setTaskName(task.getTaskName());

			logger.debug("Task Execution duration: " + (endTime - startTime) / 1000L + " seconds");

			dbutil.archiveStatus(runInfo);
		}
	}

	public int getTaskPriority(ScheduleTask task) {
		String taskName = task.getTaskName();
		return Thread.NORM_PRIORITY;
	}
}
