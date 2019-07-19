package com.cisco.dnac.scheduler.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.dnac.pnp.service.PnpService;
import com.cisco.dnac.scheduler.db.provider.DNASchedulerDBUtil;
import com.cisco.dnac.scheduler.dto.SchedulerConstants;

@Service
public class WorkflowInvoker implements Runnable {

	private static final Logger logger = Logger.getLogger(WorkflowInvoker.class);
	private String taskName;
	private int taskId;
	@Autowired
	private PnpService pnpServiceInstance;

	@Autowired
	private DNASchedulerDBUtil dbutil;

	public WorkflowInvoker(String taskName, int taskId) {
		this.taskName = taskName;
		this.taskId = taskId;

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
			logger.error("Exception while running the task ... " + taskName);
			dbutil.updateSchedulerTask(taskId, SchedulerConstants.FAILED);
			logger.error("Error ", e);

		}
	}

}
