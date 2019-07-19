package com.cisco.dnac.scheduler.service;

import java.util.List;

import com.cisco.dnac.scheduler.dto.ScheduleTask;

public interface SchedulerService {

	public void createSchedulerTask(ScheduleTask scheduleTask);

	public void deleteScheduledTask(String taskId);
	
	public List<ScheduleTask> getScheduledTask();

}
