package com.cisco.dnac.scheduler;

public interface MyScheduledExecutorServiceMBean {
	/**
	 * @return Name of executor service
	 */
	public String getName();

	/**
	 * @return corePoolSize
	 */
	public int getCorePoolSize();

	/**
	 * @return number of tasks scheduled for periodic execution
	 */
	public int getTotalTaskExecutors();
}
