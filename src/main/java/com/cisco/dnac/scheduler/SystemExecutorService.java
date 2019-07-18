package com.cisco.dnac.scheduler;

import java.util.TimerTask;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;


public final class SystemExecutorService implements SystemExecutorServiceIf {
	private static final Logger logger = Logger.getLogger(SystemExecutorService.class);
	private MyScheduledExecutorService executorService = null;
	@Value("${SystemExecutorService.corePoolSize}")
	private int corePoolSize = 10;
	private static SystemExecutorService instance = new SystemExecutorService();

	private SystemExecutorService() {

		this.executorService = new MyScheduledExecutorService("SystemExecutorService", this.corePoolSize);
	}

	public static SystemExecutorService getInstance() {
		return instance;
	}

	public Future<?> runNow(String name, TimerTask task) {
		return this.executorService.sheduleNow(name, task);
	}

	public Future<?> runNow(String name, Runnable theRunnable) {
		return this.executorService.sheduleNow(name, theRunnable);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			final String name = "Runnable#" + i;
			Runnable theRunnable = new TimerTask() {
				public void run() {
					for (int k = 0; k <= 10; k++) {
						try {
							// System.out.println(name + " running");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			SystemExecutorService.getInstance().runNow(name, theRunnable);
		}
	}
}
