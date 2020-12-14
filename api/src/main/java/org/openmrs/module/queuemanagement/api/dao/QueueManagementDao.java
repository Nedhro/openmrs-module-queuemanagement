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
	
	public List<PatientQueue> getAllQueueId() {
		List<PatientQueue> queueList = getSession().createQuery("from PatientQueue").list();
		for (PatientQueue queue : queueList) {
			logger.info("Queue List::" + queue);
		}
		return queueList;
	}
	
	public List<PatientQueue> countIdentifier(String visitroom) {
		SQLQuery criteria = getSession().createSQLQuery(
		    "select distinct identifier from patient_queue_test where visitroom =\'" + visitroom + "\'");
		return criteria.list();
	}
	
	public List<Map<String, Object>> getObsData() {
		SQLQuery q = getSession().createSQLQuery("select * from complex_obs_view");
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public List<Object> getAllVisitroom() {
		SQLQuery criteria = getSession().createSQLQuery("select distinct visitroom from patient_queue_test");
		return criteria.list();
	}
	
	public PatientQueue update(String identifier) {
		System.out.println(identifier);
		PatientQueue patientQueue = this.getPatientByIdentifier(identifier);
		if (patientQueue != null)
			this.updateStatus(patientQueue);
		System.out.println("Queue Updated: " + patientQueue);
		
		return patientQueue;
	}
	
	private PatientQueue getPatientQueueById(Integer id) {
		return (PatientQueue) getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("id", id)).uniqueResult();
	}
	
	public PatientQueue update(PatientQueue queue) {
		getSession().saveOrUpdate(queue);
		return queue;
	}
}
