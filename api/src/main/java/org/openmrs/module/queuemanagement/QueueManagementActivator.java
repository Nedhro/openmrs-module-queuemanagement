package org.openmrs.module.queuemanagement;

import org.openmrs.module.BaseModuleActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class QueueManagementActivator extends BaseModuleActivator {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	public void started() {
		log.info("Started Queue Management");
	}
	
	public void shutdown() {
		log.info("Shutdown Queue Management");
	}
	
}
