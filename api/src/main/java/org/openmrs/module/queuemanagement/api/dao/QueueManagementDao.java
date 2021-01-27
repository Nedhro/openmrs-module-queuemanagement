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
import java.util.LinkedHashMap;
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
	
	public PatientQueue getPatientByIdentifier(String identifier, Date dateCreated) {
		return (PatientQueue) getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("identifier", identifier))
		        .add(Restrictions.eq("dateCreated", dateCreated)).add(Restrictions.eq("status", true)).uniqueResult();
	}
	
	@Transactional
	public PatientQueue save(PatientQueue queue) throws Exception {
		System.out.println("Queue to Save ::" + queue);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(dateFormat.format(queue.getDateCreated()));
		System.out.println("Date New :: " + date);
		PatientQueue checkIdentifier = this.getPatientByIdentifier(queue.getIdentifier(), date);
		System.out.println("Data by Identifier: " + checkIdentifier);
		//Queue Count
		List<PatientQueue> queues = this.countIdentifier(queue.getRoomId(), date);
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
	
	public List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException {
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateCreated);
		System.out.println("Date :: " + d);
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("dateCreated", d));
		criteria.add(Restrictions.eq("status", true));
		criteria.setMaxResults(6);
		return criteria.list();
	}
	
	public PatientQueue getTokenByIdentifier(String identifier, Date dateCreated) throws APIException {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("identifier", identifier));
		criteria.add(Restrictions.eq("dateCreated", dateCreated));
		criteria.add(Restrictions.eq("status", true));
		return (PatientQueue) criteria.uniqueResult();
	}
	
	public List<PatientQueue> getAllQueueId() {
		List<PatientQueue> queueList = getSession().createQuery("from PatientQueue").list();
		for (PatientQueue queue : queueList) {
			logger.info("Queue List::" + queue);
		}
		return queueList;
	}
	
	public List<PatientQueue> countIdentifier(String roomId, Date d) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("roomId", roomId));
		criteria.add(Restrictions.eq("dateCreated", d));
		return criteria.list();
	}
	
	public List<Object> getAllVisitroom() {
		SQLQuery criteria = getSession().createSQLQuery("select distinct visitroom from queue_v7");
		return criteria.list();
	}
	
	public Map<String, Object> getPoorPatientData() {
		String query1 = "select count(obs_value.value_coded)" + " from (select o.value_coded" + " from obs o"
		        + " where o.voided = 0" + " and o.concept_id in"
		        + " (select cv.concept_id from concept_view cv where concept_full_name = 'Registration Fee Type')"
		        + " and cast(o.obs_datetime as DATE) between cast(now() as DATE) and cast(now() as DATE)"
		        + " group by o.person_id) obs_value";
		String query2 = "select count(distinct obs_value.person_id)" + " from (select o.person_id" + " from obs o"
		        + " where o.voided = 0" + " and o.value_coded in"
		        + " (select cv.concept_id from concept_view cv where concept_full_name = 'Poor Patient')"
		        + " and cast(o.obs_datetime as DATE) between cast(now() as DATE) and cast(now() as DATE)"
		        + " group by o.person_id) obs_value";
		SQLQuery criteria1 = getSession().createSQLQuery(query1);
		SQLQuery criteria2 = getSession().createSQLQuery(query2);
		Map<String, Object> obj = new LinkedHashMap<String, Object>();
		obj.put("totalPatient", criteria1.list());
		obj.put("poorPatient", criteria2.list());
		return obj;
	}
	
	@Transactional
	public PatientQueue update(PatientQueue queue) {
		getSession().saveOrUpdate(queue);
		System.out.println("Updated Queue :: " + queue);
		return queue;
	}
	
	public void truncate() throws DAOException {
		getSession().createSQLQuery("truncate table queue_v7").executeUpdate();
	}
	
	@Transactional
	public PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String roomId, Date dateCreated) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("identifier", identifier));
		criteria.add(Restrictions.eq("roomId", roomId));
		criteria.add(Restrictions.eq("dateCreated", dateCreated));
		return (PatientQueue) criteria.uniqueResult();
	}
}
