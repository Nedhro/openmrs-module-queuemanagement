package org.openmrs.module.queuemanagement.web.scheduler;

import org.openmrs.module.queuemanagement.api.service.QueueManagementService;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.springframework.beans.factory.annotation.Autowired;

public class QueueTruncate extends AbstractTask {
	
	@Autowired
	private QueueManagementService queueManagementService;
	
	@Override
	public void execute() {
		try {
			this.queueManagementService.truncate();
			System.out.println("Table truncation has been completed");
		}
		catch (Exception e) {
			System.out.println("Cause :: " + e.getCause());
		}
	}
}
