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
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.openmrs.module.queuemanagement.api.QueueManagementService;
import org.openmrs.module.queuemanagement.api.dao.QueueManagementDao;

import java.util.List;
import java.util.Map;

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

    @Override
    public PatientQueue save(PatientQueue queue) throws Exception {
        return dao.save(queue);
    }

    @Override
    public List<PatientQueue> getPatientQueueByVisitroom(String visitroom) throws Exception {
        return dao.getPatientQueueByVisitroom(visitroom);
    }

    @Override
    public PatientQueue getPatientByIdentifier(String identifier) {
        return dao.getPatientByIdentifier(identifier);
    }

    @Override
    public List<PatientQueue> getAllQueueId() throws Exception {
        return dao.getAllQueueId();
    }

    @Override
    public List<Map<String, Object>> getObsData() {
        return dao.getObsData();
    }

    @Override
    public List<Object> getAllVisitroom() {
        return dao.getAllVisitroom();
    }

    @Override
    public PatientQueue update(String identifier) {
        return dao.update(identifier);
    }

    @Override
    public PatientQueue getPatientByIdentifier(String visitroom, String identifier) {
        return dao.getTokenByIdentifier(visitroom, identifier);
    }

    @Override
    public void update(PatientQueue queue) {
        dao.update(queue);
    }

}
