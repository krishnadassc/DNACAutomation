package com.cisco.dnac.scheduler.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import com.cisco.dnac.common.entity.DNACEntity;

@Document
public class ScheduleTaskDAO implements DNACEntity {

	private static final long serialVersionUID = 4982772884472469974L;

	private int id;

	private String taskName;

	private String description;

	private long timeInMilliSeconds;

	private String status;

	private String taskDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getTimeInMilliSeconds() {
		return timeInMilliSeconds;
	}

	public void setTimeInMilliSeconds(long timeInMilliSeconds) {
		this.timeInMilliSeconds = timeInMilliSeconds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskDetails() {
		return taskDetails;
	}

	public void setTaskDetails(String taskDetails) {
		this.taskDetails = taskDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
