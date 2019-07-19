package com.cisco.dnac.scheduler.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.dnac.pnp.service.PnpService;
import com.cisco.dnac.scheduler.dto.SchedulerConstants;

@Service
public class WorkflowInvoker implements Runnable {

	private static final Logger logger = Logger.getLogger(WorkflowInvoker.class);
	private String taskName;
	@Autowired
	private PnpService pnpServiceInstance;

	public WorkflowInvoker(String taskName) {
		this.taskName = taskName;

	}

	@Override
	public void run() {
		if (SchedulerConstants.PNPSERVICE.equals(taskName)) {
			logger.info("Invoking PNP Service ... ");
			pnpServiceInstance.execute();
		} else {
			logger.warn("Un Implemented Service .... " + taskName);
		}

	}

}
