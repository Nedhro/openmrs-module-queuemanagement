package org.openmrs.module.queuemanagement.api.service.impl;

import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.queuemanagement.api.dao.QueueManagementDao;
import org.openmrs.module.queuemanagement.api.entity.PatientQueue;
import org.openmrs.module.queuemanagement.api.service.QueueManagementService;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class QueueManagementServiceImpl extends BaseOpenmrsService implements QueueManagementService {
	
	private QueueManagementDao queueManagementDao;
	
	public void setQueueManagementDao(QueueManagementDao queueManagementDao) {
		this.queueManagementDao = queueManagementDao;
	}
	
	@Transactional
	@Override
	public PatientQueue save(PatientQueue queue) throws Exception {
		this.queueManagementDao.save(queue);
		return queue;
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
