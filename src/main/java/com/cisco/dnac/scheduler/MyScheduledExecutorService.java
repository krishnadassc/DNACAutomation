package com.cisco.dnac.scheduler;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

public class MyScheduledExecutorService
		implements MyScheduledExecutorServiceMBean, Comparable<MyScheduledExecutorService> {
	private static final Logger logger = Logger.getLogger(MyScheduledExecutorService.class);
	private ScheduledExecutorService scheduledExecutorService;
	private static final int DEFAULT_COREPOOL_SIZE = 10;
	private String name;
	private int corePoolSize;
	private Map<String, ScheduledFuture<?>> taskExecutors = new HashMap<String, ScheduledFuture<?>>();

	public MyScheduledExecutorService() {
		this("MyScheduledExecutorService", DEFAULT_COREPOOL_SIZE);
	}

	public MyScheduledExecutorService(String pName) {
		this(pName, DEFAULT_COREPOOL_SIZE);
	}

	public MyScheduledExecutorService(String pName, int pCorePoolSize) {
		this.name = pName;
		this.corePoolSize = pCorePoolSize;
		this.scheduledExecutorService = Executors.newScheduledThreadPool(this.corePoolSize);
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = new ObjectName("com.cisco.cloupia.jmx:type=MyScheduledExecutorServiceMBean-" + pName);
			mbs.registerMBean(this, name);
		} catch (Exception e) {
			logger.warn("Failed to register MyScheduledExecutorService with MBeanServer", e);
		}
	}

	public void scheduleAtFixedRate(SystemTaskExecutor systemTaskExecutor, long initialDelay, long period,
			TimeUnit unit) {
		ScheduledFuture<?> scheduledFuture = this.scheduledExecutorService.scheduleAtFixedRate(systemTaskExecutor,
				initialDelay, period, unit);
		taskExecutors.put(systemTaskExecutor.getTaskName(), scheduledFuture);
	}

	public void scheduleAtFixedRate(Runnable theRunnable, String taskName, long initialDelay, long period,
			TimeUnit unit) {
		ScheduledFuture<?> scheduledFuture = this.scheduledExecutorService.scheduleAtFixedRate(theRunnable,
				initialDelay, period, unit);
		taskExecutors.put(taskName, scheduledFuture);
	}

	public void scheduleWithFixedDelay(SystemTaskExecutor systemTaskExecutor, long initialDelay, long period,
			TimeUnit unit) {
		ScheduledFuture<?> scheduledFuture = this.scheduledExecutorService.scheduleWithFixedDelay(systemTaskExecutor,
				initialDelay, period, unit);
		taskExecutors.put(systemTaskExecutor.getTaskName(), scheduledFuture);
	}

	public void scheduleWithFixedDelay(Runnable theRunnable, String taskName, long initialDelay, long period,
			TimeUnit unit) {
		ScheduledFuture<?> scheduledFuture = this.scheduledExecutorService.scheduleAtFixedRate(theRunnable,
				initialDelay, period, unit);
		taskExecutors.put(taskName, scheduledFuture);
	}

	public void scheduleNow(SystemTaskExecutor systemTaskExecutor) {
		this.scheduledExecutorService.submit(systemTaskExecutor);
	}

	public Future scheduleNow(Runnable theRunnable) {
		return this.scheduledExecutorService.submit(theRunnable);
	}

	public Future sheduleNow(String taskName, Runnable theRunnable) {
		ScheduledFuture<?> scheduledFuture = (ScheduledFuture<?>) this.scheduledExecutorService.submit(theRunnable);
		this.taskExecutors.put(taskName, scheduledFuture);
		cleanUpTaskExecutors();
		return scheduledFuture;
	}

	private void cleanUpTaskExecutors() {
		Iterator iter = this.taskExecutors.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry) iter.next();
			ScheduledFuture<?> future = (ScheduledFuture<?>) pair.getValue();
			if (future.isDone()) {
				iter.remove();
			}
		}
	}

	public void cancelTaskExecutor(String taskName) {
		this.cancelTaskExecutor(taskName, false);
	}

	public void cancelTaskExecutor(String taskName, boolean mayInterruptIfRunning) {
		ScheduledFuture<?> scheduledFuture = this.taskExecutors.get(taskName);
		if (scheduledFuture == null) {
			logger.info("Task not found to be scheduled. taskName=" + taskName);
			return;
		}
		scheduledFuture.cancel(mayInterruptIfRunning);
	}

	public void removeTaskExecutor(String taskName) {
		this.cancelTaskExecutor(taskName, true);
		this.taskExecutors.remove(taskName);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCorePoolSize() {
		return corePoolSize;
	}

	@Override
	public int getTotalTaskExecutors() {
		this.cleanUpTaskExecutors();
		return this.taskExecutors.size();
	}

	@Override
	public int compareTo(MyScheduledExecutorService that) {
		return this.getTotalTaskExecutors() - that.getTotalTaskExecutors();
	}
}
