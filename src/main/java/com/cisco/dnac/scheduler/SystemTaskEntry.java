package com.cisco.dnac.scheduler;

import com.cisco.dnac.scheduler.dto.ScheduleTask;
import com.cisco.dnac.scheduler.dto.TaskParameters;

public class SystemTaskEntry {

	private String category;

	private String label;

	private String description;

	private ScheduleTask task;

	private boolean parameterized;

	private TaskParameters[] params;

	public SystemTaskEntry(String category, String label, String description, ScheduleTask task, boolean parameterized,
			TaskParameters[] params) {
		super();
		this.category = category;
		this.label = label;
		this.description = description;
		this.task = task;
		this.parameterized = parameterized;
		this.params = params;
	}

	public ScheduleTask getTask() {
		return task;
	}

	public void setTask(ScheduleTask task) {
		this.task = task;
	}

	public boolean isParameterized() {
		return parameterized;
	}

	public void setParameterized(boolean parameterized) {
		this.parameterized = parameterized;
	}

	public TaskParameters[] getParams() {
		return params;
	}

	public void setParams(TaskParameters[] params) {
		this.params = params;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
