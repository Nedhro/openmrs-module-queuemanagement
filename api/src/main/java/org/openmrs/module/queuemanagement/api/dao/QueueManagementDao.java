/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.queuemanagement.api.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("queuemanagement.QueueManagementDao")
public class QueueManagementDao {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/*public Item getItemByUuid(String uuid) {
	    return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	public Item saveItem(Item item) {
	    getSession().saveOrUpdate(item);
	    return item;
	}*/
	public PatientQueue getPatientByIdentifier(String identifier) {
		return (PatientQueue) getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("identifier", identifier))
		        .add(Restrictions.eq("status", true)).uniqueResult();
	}
	
	public PatientQueue save(PatientQueue queue) throws Exception {
		PatientQueue checkIdentifier = this.getPatientByIdentifier(queue.getIdentifier());
		System.out.println("Data by Identifier: " + checkIdentifier);
		//Queue Count
		List<PatientQueue> queues = this.countIdentifier(queue.getVisitroom());
		System.out.println("Queue size in the room: " + queues + " Size:" + queues.size());
		int token = queues.size() + 1;
		if (checkIdentifier != null) {
			this.updateStatus(checkIdentifier);
			
			queue.setToken(token);
			getSession().persist(queue);
			System.out.println("Patient Queue Added: " + queue);
		}
		if (queue.getId() == null) {
			queue.setToken(token);
			getSession().persist(queue);
			System.out.println("Patient Queue Added: " + queue);
		}
		
		return queue;
	}
	
	public PatientQueue updateStatus(PatientQueue queue) {
		System.out.println(queue);
		queue.setId(queue.getId());
		queue.setToken(queue.getToken());
		queue.setIdentifier(queue.getIdentifier());
		queue.setVisitroom(queue.getVisitroom());
		queue.setStatus(false);
		getSession().update(queue);
		System.out.println("Patient Queue Status Updated: " + queue);
		return queue;
	}
	
	/*public boolean existByIdentifier(String identifier, String visitroom) {
		SQLQuery criteria = getSession().createSQLQuery(
		    "select identifier, token from queue2 q where q.visitroom=\'" + visitroom + "\' and q.identifier=\'"
		            + identifier + "\'");
		System.out.println("Criteria: " + criteria.list());
		if (criteria.list().size() > 0) {
			return true;
		}
		return false;
	}*/
	
	/*public PatientQueue existsIdentifier(String identifier) {
		SQLQuery criteria = getSession().createSQLQuery("select * from queue2 q where q.identifier=\'" + identifier + "\'");
		PatientQueue listQueues = (PatientQueue) criteria.list();
		System.out.println("Criteria: " + criteria.list());
		return listQueues;
	}*/
	
	public List<PatientQueue> getPatientQueueByVisitroom(String visitroom) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("status", true));
		return criteria.list();
	}
	
	public PatientQueue getTokenByIdentifier(String visitroom, String identifier) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("identifier", identifier));
		return (PatientQueue) criteria.uniqueResult();
	}
	
	/*public Integer getMaxTokenByVisitroom(String visitroom) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("visitroom", visitroom))
		        .setProjection(Projections.max("token"));
		Integer maxToken = (Integer) criteria.uniqueResult();
		System.out.println(maxToken);
		return maxToken;
	}*/
	
	public List<PatientQueue> getAllQueueId() {
		List<PatientQueue> queueList = getSession().createQuery("from PatientQueue").list();
		for (PatientQueue queue : queueList) {
			logger.info("Queue List::" + queue);
		}
		return queueList;
	}
	
	public List<PatientQueue> countIdentifier(String visitroom) {
		SQLQuery criteria = getSession().createSQLQuery(
		    "select distinct identifier from queue2 where visitroom =\'" + visitroom + "\'");
		return criteria.list();
	}
	
	public List<Map<String, Object>> getObsData() {
		SQLQuery q = getSession().createSQLQuery("select * from complex_obs_view");
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public List<Object> getAllVisitroom() {
		SQLQuery criteria = getSession().createSQLQuery("select distinct visitroom from queue2");
		return criteria.list();
	}
	
	public PatientQueue update(String identifier) {
		System.out.println(identifier);
		PatientQueue patientQueue = this.getPatientByIdentifier(identifier);
		System.out.println("patient queue :::::: " + patientQueue);
		if (patientQueue != null) {
			//queue.setId(id);
			patientQueue.setStatus(false);
			getSession().update(patientQueue);
			System.out.println("Queue Updated: " + patientQueue);
		}
		return patientQueue;
	}
	
	private PatientQueue getPatientQueueById(Integer id) {
		return (PatientQueue) getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("id", id)).uniqueResult();
	}
	
	public void update(PatientQueue queue) {
		getSession().update(queue);
	}
}
