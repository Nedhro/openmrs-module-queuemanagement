package org.openmrs.module.queuemanagement.api.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.queuemanagement.api.dao.QueueManagementDao;
import org.openmrs.module.queuemanagement.api.entity.PatientQueue;
import org.openmrs.module.queuemanagement.api.entity.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository("queueManagementDao")
public class QueueManagementDaoImpl implements QueueManagementDao {

	protected final Logger log = LoggerFactory.getLogger(QueueManagementDaoImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public PatientQueue getPatientByIdentifier(String identifier, Date dateCreated) {
		return (PatientQueue) getSession().createCriteria(PatientQueue.class).add(Restrictions.eq("identifier", identifier))
		        .add(Restrictions.eq("dateCreated", dateCreated)).add(Restrictions.eq("status", Status.ACTIVE.getValue()))
		        .uniqueResult();
	}

	@Transactional
	public PatientQueue save(PatientQueue queue) throws Exception {
		log.info("Queue to Save ::" + queue);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(dateFormat.format(queue.getDateCreated()));
		log.info("Date New :: " + date);
		PatientQueue checkIdentifier = this.getPatientByIdentifier(queue.getIdentifier(), date);
		log.info("Data by Identifier: " + checkIdentifier);
		//Queue Count
		List<PatientQueue> queues = this.countIdentifier(queue.getRoomId(), date);
		log.info("Queue size in the room: " + queues + " Size:" + queues.size());
		int token = queues.size() + 1;
		if (checkIdentifier != null) {
			this.updateStatus(checkIdentifier);

			queue.setToken(token);
			getSession().persist(queue);
			log.info("Patient Queue Added: " + queue);
		}
		if (queue.getId() == null) {
			queue.setToken(token);
			getSession().persist(queue);
			log.info("Patient Queue Added: " + queue);
		}
		return queue;
	}

	@Transactional
	public PatientQueue updateStatus(PatientQueue queue) {
		log.info("Queue to Update:: " + queue);
		queue.setStatus(Status.SCHEDULED.getValue());
		getSession().saveOrUpdate(queue);
		log.info("Patient Queue Status Updated: " + queue);
		return queue;
	}

	public List<PatientQueue> getPatientQueueByVisitroom(String visitroom, String dateCreated) throws ParseException {
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dateCreated);
		log.info("Date :: " + d);
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("visitroom", visitroom));
		criteria.add(Restrictions.eq("dateCreated", d));
		criteria.add(Restrictions.eq("status", Status.ACTIVE.getValue()));
		criteria.setMaxResults(6);
		return criteria.list();
	}

	public PatientQueue getTokenByIdentifier(String identifier, Date dateCreated) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("identifier", identifier));
		criteria.add(Restrictions.eq("dateCreated", dateCreated));
		criteria.add(Restrictions.eq("status", Status.ACTIVE.getValue()));
		return (PatientQueue) criteria.uniqueResult();
	}

	@Override
	public String getHospitalData() {
		LocationService locationService = Context.getService(LocationService.class);
		List<Location> location = locationService.getLocationsByTag(locationService.getLocationTagByName("Visit Location"));
		Location visitLocation = location.get(location.size() - 1);
		return visitLocation.getName();
	}

	public List<PatientQueue> getAllQueueId() {
		List<PatientQueue> queueList = getSession().createQuery("from PatientQueue").list();
		log.info("Queue List ::" + queueList);
		return queueList;
	}

	public List<PatientQueue> countIdentifier(String roomId, Date d) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("roomId", roomId));
		criteria.add(Restrictions.eq("dateCreated", d));
		return criteria.list();
	}

	public List<Object> getAllVisitroom() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateCreated = dateFormat.parse(dateFormat.format(new Date()));
		log.info("Date :: " + dateCreated);
		List<Object> roomList = getSession()
		        .createQuery("SELECT distinct pq.visitroom FROM PatientQueue pq WHERE pq.dateCreated=:dateCreated")
		        .setParameter("dateCreated", dateCreated).list();

		log.info("Rooms :: " + roomList);
		//		SQLQuery criteria = getSession().createSQLQuery("select distinct visitroom from opd_patients_queue");
		return roomList;
	}

	@Transactional
	public PatientQueue update(PatientQueue queue) {
		getSession().saveOrUpdate(queue);
		log.info("Updated Queue :: " + queue);
		return queue;
	}

	public void truncate() throws DAOException {
		getSession().createSQLQuery("truncate table opd_patients_queue").executeUpdate();
	}

	public PatientQueue getPatientByIdentifierAndVisitroom(String identifier, String roomId, Date dateCreated) {
		Criteria criteria = getSession().createCriteria(PatientQueue.class);
		criteria.add(Restrictions.eq("identifier", identifier));
		criteria.add(Restrictions.eq("roomId", roomId));
		criteria.add(Restrictions.eq("dateCreated", dateCreated));
		return (PatientQueue) criteria.uniqueResult();
	}
}
