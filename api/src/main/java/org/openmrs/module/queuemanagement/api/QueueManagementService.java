/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.queuemanagement.api;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface QueueManagementService extends OpenmrsService {
	
	/*
	*//**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	/*
	@Authorized()
	@Transactional(readOnly = true)
	Item getItemByUuid(String uuid) throws APIException;
	Item getPatientQueueByVisitroom(String uuid) throws APIException;

	*//**
	 * Saves an item. Sets the owner to superuser, if it is not set. It can be called by users
	 * with this module's privilege. It is executed in a transaction.
	 * 
	 * @param item
	 * @return
	 * @throws APIException
	 */
	/*
	@Authorized(QueueManagementConfig.MODULE_PRIVILEGE)
	@Transactional
	Item saveItem(Item item) throws APIException;*/
	
	@Transactional
	PatientQueue save(PatientQueue queue) throws Exception;
	
	@Transactional
	List<PatientQueue> getPatientQueueByVisitroom(String visitroom) throws Exception;
	
	@Transactional
	PatientQueue getPatientByIdentifier(String identifier);
	
	@Transactional
	List<PatientQueue> getAllQueueId() throws Exception;
	
	@Transactional
	List<Map<String, Object>> getObsData();
	
	@Transactional
	List<Object> getAllVisitroom();
	
	PatientQueue update(String identifier);
	
	PatientQueue getPatientByIdentifier(String visitroom, String identifier);
	
	void update(PatientQueue queue);
}
