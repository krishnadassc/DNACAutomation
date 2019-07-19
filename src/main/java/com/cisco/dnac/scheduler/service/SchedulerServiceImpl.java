package com.cisco.dnac.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cisco.dnac.scheduler.SystemExecutorServiceIf;
import com.cisco.dnac.scheduler.dao.ScheduleTaskDAO;
import com.cisco.dnac.scheduler.db.provider.DNASchedulerDBUtil;
import com.cisco.dnac.scheduler.dto.ScheduleTask;
import com.cisco.dnac.scheduler.dto.SchedulerConstants;

public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private DNASchedulerDBUtil dbutil;

	@Autowired
	private SystemExecutorServiceIf scheduler;
	
	@Autowired
	private WorkflowInvoker invoker;

	public void createSchedulerTask(ScheduleTask scheduleTask) {
		dbutil.createSchedulerTask(scheduleTask);
		// invoke schedule job
		String taskUniqueName = scheduleTask.getTaskName() + "-" + scheduleTask.getId();
		long scheduledTimeInMilliSec = scheduleTask.getTimeInMilliseconds();
		
		
		scheduler.sheduleTask(taskUniqueName, invoker, scheduledTimeInMilliSec);
		dbutil.updateSchedulerTask(scheduleTask.getId(), SchedulerConstants.SCHEDULED);
	}

	public void deleteScheduledTask(String taskId) {
		dbutil.deleteScheduledTask(taskId);

	}

	public List<ScheduleTask> getScheduledTask() {
		List<ScheduleTaskDAO> list = dbutil.getScheduledTaskList();
		if (list == null || list.size() == 0) {
			return null;
		}

		List<ScheduleTask> taskList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScheduleTaskDAO taskDao = list.get(i);
			ScheduleTask task = new ScheduleTask();

			task.setDescription(taskDao.getDescription());
			task.setTaskDetails(taskDao.getTaskDetails());
			task.setTaskName(taskDao.getTaskName());
			task.setTimeInMilliseconds(taskDao.getTimeInMilliSeconds());
			task.setId(taskDao.getId());
			task.setStatus(taskDao.getStatus());
			taskList.add(task);
		}

		return taskList;
	}

}
