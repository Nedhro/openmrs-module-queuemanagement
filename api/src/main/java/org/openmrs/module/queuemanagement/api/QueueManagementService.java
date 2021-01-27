/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.queuemanagement.api;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface QueueManagementService extends OpenmrsService {
	
	/*
	/*
	@Authorized()
	@Transactional(readOnly = true)
	Item getItemByUuid(String uuid) throws APIException;

	*/
	/*
	@Authorized(QueueManagementConfig.MODULE_PRIVILEGE)
	@Transactional
	Item saveItem(Item item) throws APIException;
	*/
	@Transactional
	PatientQueue save(PatientQueue queue) throws Exception;
	
	@Transactional
	List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException;
	
	@Transactional
	List<PatientQueue> getAllQueueId() throws APIException;
	
	@Transactional
	List<Object> getAllVisitroom() throws APIException;
	
	@Transactional
	Map<String, Object> getPoorPatientData() throws APIException;
	
	@Transactional
	void update(PatientQueue queue) throws APIException;
	
	void truncate() throws DAOException;
	
	@Transactional
	PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String roomId, Date dateCreated);
	
	@Transactional
	PatientQueue getTokenByIdentifier(String identifier, Date date);
}
