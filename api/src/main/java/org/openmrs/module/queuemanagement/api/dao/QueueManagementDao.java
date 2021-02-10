package org.openmrs.module.queuemanagement.api.dao;

import org.openmrs.module.queuemanagement.api.entity.PatientQueue;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface QueueManagementDao {
	
	@Transactional
	PatientQueue save(PatientQueue queue) throws Exception;
	
	List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException;
	
	List<PatientQueue> getAllQueueId();
	
	List<Object> getAllVisitroom() throws ParseException;
	
	@Transactional
	PatientQueue update(PatientQueue queue);
	
	@Transactional
	void truncate();
	
	PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String roomId, Date dateCreated);
	
	PatientQueue getTokenByIdentifier(String identifier, Date date);
	
	String getHospitalData();
}
