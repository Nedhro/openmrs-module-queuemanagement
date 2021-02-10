package org.openmrs.module.queuemanagement.api.service;

import org.openmrs.api.OpenmrsService;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.queuemanagement.api.entity.PatientQueue;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
	
	@Transactional(readOnly = true)
	List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException;
	
	@Transactional(readOnly = true)
	List<PatientQueue> getAllQueueId();
	
	@Transactional(readOnly = true)
	List<Object> getAllVisitroom() throws ParseException;
	
	@Transactional
	void update(PatientQueue queue) throws Exception;
	
	@Transactional
	void truncate() throws DAOException;
	
	@Transactional(readOnly = true)
	PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String roomId, Date dateCreated);
	
	@Transactional(readOnly = true)
	PatientQueue getTokenByIdentifier(String identifier, Date date);
	
	@Transactional(readOnly = true)
	String getHospitalData();
}
