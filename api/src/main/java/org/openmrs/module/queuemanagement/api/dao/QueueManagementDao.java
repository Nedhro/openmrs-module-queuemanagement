package org.openmrs.module.queuemanagement.api.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
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
	
	public PatientQueue getPatientByIdentifier(String identifier, Date dateCreated) {
		return (PatientQueue) getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("identifier", identifier))
		        .add(Restrictions.eq("dateCreated", dateCreated)).add(Restrictions.eq("status", true)).uniqueResult();
	}
	
	@Transactional
	public PatientQueue save(PatientQueue queue) {
		try {
			String date = String.valueOf(new Date());
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			PatientQueue checkIdentifier = this.getPatientByIdentifier(queue.getIdentifier(), d);
			System.out.println("Data by Identifier: " + checkIdentifier);
			//Queue Count
			List<PatientQueue> queues = this.countIdentifier(queue.getVisitroom(), d);
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
		}
		catch (Exception e) {
			e.printStackTrace();
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
	
	public List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException {
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateCreated);
		System.out.println("Date :: " + d);
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("status", true));
		criteria.add(Restrictions.eq("dateCreated", d));
		return criteria.list();
	}
	
	public PatientQueue getTokenByIdentifier(String identifier, Date dateCreated) throws APIException {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("identifier", identifier));
		criteria.add(Restrictions.eq("dateCreated", dateCreated));
		return (PatientQueue) criteria.uniqueResult();
	}
	
	public List<PatientQueue> getAllQueueId() {
		List<PatientQueue> queueList = getSession().createQuery("from PatientQueue").list();
		for (PatientQueue queue : queueList) {
			logger.info("Queue List::" + queue);
		}
		return queueList;
	}
	
	public List<PatientQueue> countIdentifier(String visitroom, Date d) throws ParseException {
		SQLQuery criteria = getSession()
		        .createSQLQuery(
		            "select distinct identifier from queue_v4 where visitroom =\'" + visitroom + "\' and date_created=\'"
		                    + d + "\'");
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
	
	@Transactional
	public PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String visitroom, Date dateCreated) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("identifier", identifier));
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("dateCreated", dateCreated));
		return (PatientQueue) criteria.uniqueResult();
	}
}
