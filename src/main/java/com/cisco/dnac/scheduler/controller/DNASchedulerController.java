package com.cisco.dnac.scheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.dnac.common.CommonUrl;
import com.cisco.dnac.scheduler.dto.ScheduleTask;
import com.cisco.dnac.scheduler.service.SchedulerService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(CommonUrl.SCHEDULER_URL)
public class DNASchedulerController {

	@Autowired
	private SchedulerService schedulerService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json")
	public void createSchedulerTask(@ApiParam @RequestBody ScheduleTask scheduleTask) {
		schedulerService.createSchedulerTask(scheduleTask);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public void deleteScheduledTask(@ApiParam @RequestBody String taskId) {
		schedulerService.deleteScheduledTask(taskId);
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, produces = "application/json")
	public List<ScheduleTask> getScheduledTask() {
		List<ScheduleTask> scheduledTaskList = schedulerService.getScheduledTask();
		return scheduledTaskList;
	}

}
