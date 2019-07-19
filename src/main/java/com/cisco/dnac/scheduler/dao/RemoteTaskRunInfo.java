package com.cisco.dnac.scheduler.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import com.cisco.dnac.common.entity.DNACEntity;

@Document
public class RemoteTaskRunInfo implements DNACEntity {

	private static final long serialVersionUID = -8495216496352876627L;

	private int id;

	private String agentLabel;

	private String taskName;

	private String status;

	private String message;

	private long duration;

	private long timestamp;

	private long startTime;

	private long endTime;

	private int durationInSecs;

	private String executedAt;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAgentLabel() {
		return agentLabel;
	}

	public void setAgentLabel(String agentLabel) {
		this.agentLabel = agentLabel;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getDurationInSecs() {
		return durationInSecs;
	}

	public void setDurationInSecs(int durationInSecs) {
		this.durationInSecs = durationInSecs;
	}

	public String getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(String executedAt) {
		this.executedAt = executedAt;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

}
