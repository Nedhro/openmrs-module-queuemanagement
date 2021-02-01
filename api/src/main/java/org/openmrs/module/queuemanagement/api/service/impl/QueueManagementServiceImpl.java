package org.openmrs.module.queuemanagement.api.service.impl;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.openmrs.module.queuemanagement.api.dao.QueueMangementDao;
import org.openmrs.module.queuemanagement.api.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class QueueManagementServiceImpl implements QueueManagementService {
	
	@Autowired
	private QueueMangementDao queueManagementDao;
	
	public void setQueueManagementDao(QueueMangementDao queueManagementDao) {
		this.queueManagementDao = queueManagementDao;
	}
	
	@Transactional
	@Override
	public PatientQueue save(PatientQueue queue) throws Exception {
		return queueManagementDao.save(queue);
	}
	
	@Override
	public List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException {
		return queueManagementDao.getPatientQueueByVisitroom(visitroom, dateCreated);
	}
	
	@Override
	public List<PatientQueue> getAllQueueId() {
		return queueManagementDao.getAllQueueId();
	}
	
	@Override
	public List<Object> getAllVisitroom() {
		return queueManagementDao.getAllVisitroom();
	}
	
	@Transactional
	@Override
	public void update(PatientQueue queue) {
		queueManagementDao.update(queue);
	}
	
	@Override
	public void truncate() throws DAOException {
		queueManagementDao.truncate();
	}
	
	@Transactional
	@Override
	public PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String roomId, Date dateCreated) {
		return queueManagementDao.getPatientByIdentifierAndVisitroom(identifier, roomId, dateCreated);
	}
	
	@Override
	public PatientQueue getTokenByIdentifier(String identifier, Date date) {
		return queueManagementDao.getTokenByIdentifier(identifier, date);
	}
	
}