package com.cisco.dnac.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cisco.dnac.scheduler.dto.ScheduleTask;

public class SystemTaskRegistry {

	private Map<String, SystemTaskEntry> registry = new HashMap<String, SystemTaskEntry>();

	private static SystemTaskRegistry instance = new SystemTaskRegistry();

	public static SystemTaskRegistry getInstance() {
		return instance;
	}

	public void addTask(SystemTaskEntry entry) {

		registry.put(entry.getTask().getTaskName(), entry);
	}

	public SystemTaskEntry getTaskEntry(String name) {
		return registry.get(name);
	}

	public Collection<SystemTaskEntry> getAllTasks() {
		return registry.values();
	}

	public ScheduleTask getTaskByName(String name) {
		SystemTaskEntry entry = getTaskEntry(name);
		if (entry == null) {
			return null;
		}
		return entry.getTask();
	}

	public void removeTask(String name) {
		registry.remove(name);
	}

	public List<ScheduleTask> getValidTasks() {
		List<ScheduleTask> tasks = new ArrayList<ScheduleTask>();
		for (SystemTaskEntry entry : registry.values()) {
			if (entry.getTask().isValid()) {
				tasks.add(entry.getTask());
			}
		}
		return tasks;
	}

}
