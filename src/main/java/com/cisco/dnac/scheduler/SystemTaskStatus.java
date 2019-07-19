package com.cisco.dnac.scheduler;

public class SystemTaskStatus {

	private String taskName;
	private int status;
	private String message;

	public SystemTaskStatus(String taskName, int status, String message) {
		super();
		this.taskName = taskName;
		this.status = status;
		this.message = message;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
