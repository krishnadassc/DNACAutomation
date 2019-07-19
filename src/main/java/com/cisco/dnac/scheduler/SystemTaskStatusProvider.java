package com.cisco.dnac.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.dnac.scheduler.dao.SystemStateEntry;
import com.cisco.dnac.scheduler.db.provider.DNASchedulerDBUtil;
import com.cisco.dnac.scheduler.dto.SchedulerConstants;

@Service
public class SystemTaskStatusProvider {

	@Autowired
	private DNASchedulerDBUtil dbutil;

	private static final Logger logger = Logger.getLogger(SystemTaskStatusProvider.class);
	public static final String STATUS_PREAPRE = "Prepare";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_IN_PROGRESS = "In Progress";
	public static final String STATUS_IN_SCHEDULED = "Scheduled";
	public static final String STATUS_CANCELLED = "Cancelled";
	public static final String STATUS_ERROR = "ERROR - Service Node went down when the task execution was in progress";

	public void setOK(String taskName) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setValue(STATUS_OK);
		status.setEndTime(System.currentTimeMillis());
		long duration = (long) ((status.getEndTime() - status.getStartTime()) / 1000L);
		status.setDuration(duration);
		status.setLastUpdated(System.currentTimeMillis());
		status.setInitialDelay(0L);
		dbutil.updateStatus(status);

	}

	public void setInProgress(String taskName) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setValue(STATUS_IN_PROGRESS);
		status.setLastUpdated(System.currentTimeMillis());
		status.setStartTime(System.currentTimeMillis());
		// status.setEndTime(-1);
		status.setDuration(-1);
		dbutil.updateStatus(status);

	}

	public void updateNodeInfo(String taskName, String agentIpAddress, String nodeName) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setAgentIpAddress(agentIpAddress);
		status.setNodeName(nodeName);
		status.setLastUpdated(System.currentTimeMillis());
		dbutil.updateStatus(status);

	}

	public void setInQueue(String taskName, long delay) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setLastUpdated(System.currentTimeMillis());
		status.setInitialDelay(delay);
		status.setDuration(-1);
		status.setValue(STATUS_IN_SCHEDULED);
		dbutil.updateStatus(status);
	}

	public void setCancelled(String taskName) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setValue(STATUS_CANCELLED);
		status.setLastUpdated(System.currentTimeMillis());
		dbutil.updateStatus(status);
	}

	public boolean isTaskAlreadyRunning(String taskName) {

		SystemStateEntry entry = dbutil.getStatus(taskName);
		String currentStatus = entry.getValue();
		return STATUS_IN_PROGRESS.equalsIgnoreCase(currentStatus);

	}

	public void setError(String taskName, String msg) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setValue(msg);
		status.setInitialDelay(0L);
		status.setEndTime(System.currentTimeMillis());
		long duration = (long) ((status.getEndTime() - status.getStartTime()) / 1000L);
		status.setDuration(duration);
		status.setLastUpdated(System.currentTimeMillis());
		dbutil.updateStatus(status);
	}

	public void setPrepare(String taskName, String category, String label) {
		setPrepare(taskName, category, label, null);
	}

	public void setPrepare(String taskName, String category, String label, String description) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setCategory(category);
		status.setLabel(label);
		status.setValue(STATUS_PREAPRE);
		status.setDescription(description);
		dbutil.updateStatus(status);
	}

	public void setOK(String taskName, String category, String label, String description) {
		SystemStateEntry status = dbutil.getStatus(taskName);
		status.setCategory(category);
		status.setLabel(label);
		status.setValue(STATUS_OK);
		status.setDescription(description);
		status.setAgentIpAddress(SchedulerConstants.LOCAL_HOST_NAME);
		status.setNodeName(SchedulerConstants.LOCAL_HOST_NAME);
		dbutil.updateStatus(status);
	}

	public int resetTaskStatus(String taskName) {
		if (isTaskAlreadyRunning(taskName)) {
			return -1;
		}
		dbutil.deleteSystemStateProperty("task." + taskName);
		return 1;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * ObjStoreHelper.init(); resetTaskStatus(null);
		 */
		long duration = (long) ((System.currentTimeMillis() - 1382106796709L) / 1000L);
		// System.out.println(duration);
		logger.debug(duration);
	}

	public void deleteTask(String taskName) {

		dbutil.deleteSystemStateProperty("task." + taskName);
	}

	public static boolean isLocalRunPolicySet(String taskName) {
		return true;

	}

}
