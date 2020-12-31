/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.queuemanagement.api.impl;

import org.openmrs.api.UserService;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.queuemanagement.api.QueueManagementService;
import org.openmrs.module.queuemanagement.api.dao.QueueManagementDao;
import org.openmrs.module.queuemanagement.model.PatientQueue;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class QueueManagementServiceImpl extends BaseOpenmrsService implements QueueManagementService {
	
	QueueManagementDao dao;
	
	UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(QueueManagementDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Transactional
	@Override
	public PatientQueue save(PatientQueue queue) throws Exception {
		return dao.save(queue);
	}
	
	@Override
	public List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException {
		return dao.getPatientQueueByVisitroom(visitroom, dateCreated);
	}
	
	@Override
	public List<PatientQueue> getAllQueueId() {
		return dao.getAllQueueId();
	}
	
	@Override
	public List<Object> getAllVisitroom() {
		return dao.getAllVisitroom();
	}
	
	@Transactional
	@Override
	public void update(PatientQueue queue) {
		dao.update(queue);
	}
	
	@Override
	public void truncate() throws DAOException {
		dao.truncate();
	}
	
	@Transactional
	@Override
	public PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String visitroom, Date dateCreated) {
		return dao.getPatientByIdentifierAndVisitroom(identifier, visitroom, dateCreated);
	}
	
	@Override
	public PatientQueue getTokenByIdentifier(String identifier, Date date) {
		return dao.getTokenByIdentifier(identifier, date);
	}
	
}
