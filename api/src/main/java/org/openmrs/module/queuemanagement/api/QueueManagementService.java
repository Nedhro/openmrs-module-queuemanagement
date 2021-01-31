package org.openmrs.module.queuemanagement.api;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface QueueManagementService {
	
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
	void update(PatientQueue queue) throws APIException;
	
	void truncate() throws DAOException;
	
	@Transactional
	PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String roomId, Date dateCreated);
	
	@Transactional
	PatientQueue getTokenByIdentifier(String identifier, Date date);
}
