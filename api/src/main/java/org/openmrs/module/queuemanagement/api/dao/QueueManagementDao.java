package org.openmrs.module.queuemanagement.api.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository("queuemanagement.QueueManagementDao")
public class QueueManagementDao {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public PatientQueue getPatientByIdentifier(String identifier) {
		Date date = new Date();
		return (PatientQueue) getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("identifier", identifier))
		        .add(Restrictions.eq("status", true)).add(Restrictions.eq("dateCreated", date)).uniqueResult();
	}
	
	@Transactional
	public PatientQueue save(PatientQueue queue) {
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
	
	@Transactional
	public PatientQueue updateStatus(PatientQueue queue) {
		System.out.println(queue);
		queue.setStatus(false);
		getSession().saveOrUpdate(queue);
		System.out.println("Patient Queue Status Updated: " + queue);
		return queue;
	}
	
	public List<PatientQueue> getPatientQueueByVisitroom(String visitroom) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateFormat);
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("status", true));
		criteria.add(Restrictions.eq("dateCreated", dateFormat));
		return criteria.list();
	}
	
	public PatientQueue getTokenByIdentifier(String visitroom, String identifier) {
		Date date = new Date();
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("identifier", identifier));
		criteria.add(Restrictions.eq("dateCreated", date));
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
		Date date = new Date();
		SQLQuery criteria = getSession().createSQLQuery(
		    "select distinct identifier from queue_v4 where visitroom =\'" + visitroom + "\' and date_created =\'" + date
		            + "\'");
		return criteria.list();
	}
	
	public List<Object> getAllVisitroom() {
		SQLQuery criteria = getSession().createSQLQuery("select distinct visitroom from queue_v4");
		return criteria.list();
	}
	
	@Transactional
	public PatientQueue update(PatientQueue queue) {
		getSession().saveOrUpdate(queue);
		return queue;
	}
	
	public void truncate() throws DAOException {
		getSession().createSQLQuery("truncate table queue_v4").executeUpdate();
	}
}
