
package com.cisco.dnac.scheduler.dto;

public interface ScheduleTask {
	public static final int EXECUTION_MODE_ALL = 10;
	public static final int EXECUTION_MODE_COLLECTOR = 11;
	public static final int EXECUTION_MODE_JOBMGR = 12;
	public static final long FREQUENCY_MIN = 60 * 1000L;
	public static final long FREQUENCY_15MIN = 15 * FREQUENCY_MIN;
	public static final long FREQUENCY_30MIN = 30 * FREQUENCY_MIN;
	public static final long FREQUENCY_HOURLY = 60 * FREQUENCY_MIN;
	public static final long FREQUENCY_DAILY = 24 * FREQUENCY_HOURLY;
	public static final long FREQUENCY_WEEKLY = 7 * FREQUENCY_DAILY;
	public static final long FREQUENCY_MONTHLY = 30 * FREQUENCY_DAILY;
	public static final long FREQUENCY_THOUROUGLY = 30 * FREQUENCY_DAILY;

	public static final int FREQUENCY_FOUR_HOURLY = 4;
	public static final int FREQUENCY_TWENTY_FOUR_HOURLY = 24;
	public static final int FREQUENCY_NON_RECURRING = -1;

	/**
	 * The identifier for this task.
	 * 
	 * @return A unique string to uniquely identify this task. It is a good idea to
	 *         prefix your task name with some unique descriptor followed by its
	 *         purpose. E.g. moduleID_inventoryCollection.
	 */
	public String getTaskName();

	/**
	 * The frequency this task will be executed in milliseconds.
	 * 
	 * @return Number of milliseconds this task should wait between executions.
	 */
	public long getFrequency();

	public void execute(long lastExecuted) throws Exception;

	public boolean isValid();

	/**
	 * While implementaing this method, choose the return value very carefully. If
	 * you are not sure what to pick, Contact for help. Expected values are:
	 * 
	 * EXECUTION_MODE_ALL, EXECUTION_MODE_COLLECTOR, EXECUTION_MODE_JOBMGR
	 * 
	 * @return
	 */
	public int getTaskExecutionMode();

}
