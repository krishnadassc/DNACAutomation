package com.cisco.dnac.scheduler.service;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.dnac.pnp.service.PnpService;
import com.cisco.dnac.scheduler.db.provider.DNASchedulerDBUtil;
import com.cisco.dnac.scheduler.dto.SchedulerConstants;

@Service
public class WorkflowInvoker implements Runnable {

	private static final Logger logger = Logger.getLogger(WorkflowInvoker.class);
	private String taskName;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ObjectId getTaskId() {
		return taskId;
	}

	public void setTaskId(ObjectId taskId) {
		this.taskId = taskId;
	}

	private ObjectId taskId;
	
	private PnpService pnpServiceInstance;

	public void setPnpServiceInstance(PnpService pnpServiceInstance) {
		this.pnpServiceInstance = pnpServiceInstance;
	}

	private DNASchedulerDBUtil dbutil;

	public void setDbutil(DNASchedulerDBUtil dbutil) {
		this.dbutil = dbutil;
	}

	public WorkflowInvoker() {

	}

	public void run() {
		try {
			if (SchedulerConstants.PNPSERVICE.equals(taskName)) {
				logger.info("Invoking PNP Service ... ");
				dbutil.updateSchedulerTask(taskId, SchedulerConstants.INPROGRSS);
				pnpServiceInstance.execute();
				dbutil.updateSchedulerTask(taskId, SchedulerConstants.COMPLETED);
			} else {
				logger.warn("Un Implemented Service .... " + taskName);
			}

		} catch (Exception e) {			
			logger.error("Exception while running the task ... " + taskName + "TaskId  "+taskId);

		}		
	}

}
