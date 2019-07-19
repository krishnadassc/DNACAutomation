package com.cisco.dnac.scheduler;

import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public final class SystemExecutorService implements SystemExecutorServiceIf {
	private static final Logger logger = Logger.getLogger(SystemExecutorService.class);
	private MyScheduledExecutorService executorService = null;

	private int corePoolSize = 10;
	private static SystemExecutorService instance = new SystemExecutorService();

	private SystemExecutorService() {

		this.executorService = new MyScheduledExecutorService("SystemExecutorService", this.corePoolSize);
	}

	public static SystemExecutorService getInstance() {
		return instance;
	}

	public Future<?> sheduleTask(String taskName, Runnable runnable, long scheduledTimeInMilliSec) {
		return this.executorService.sheduleTask(taskName, runnable, scheduledTimeInMilliSec);

	}

}
