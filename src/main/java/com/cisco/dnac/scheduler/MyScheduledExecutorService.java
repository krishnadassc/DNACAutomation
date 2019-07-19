package com.cisco.dnac.scheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class MyScheduledExecutorService implements Comparable<MyScheduledExecutorService> {
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

	}

	public Future sheduleTask(String taskName, Runnable theRunnable, long scheduleTime) {
		long delay = scheduleTime - System.currentTimeMillis();
		ScheduledFuture<?> scheduledFuture = (ScheduledFuture<?>) this.scheduledExecutorService.schedule(theRunnable,
				delay, TimeUnit.MILLISECONDS);
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

	public String getName() {
		return name;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public int getTotalTaskExecutors() {
		this.cleanUpTaskExecutors();
		return this.taskExecutors.size();
	}

	public int compareTo(MyScheduledExecutorService that) {
		return this.getTotalTaskExecutors() - that.getTotalTaskExecutors();
	}
}
