package org.openmrs.module.queuemanagement.api.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.queuemanagement.model.HospitalInfo;
import org.springframework.stereotype.Repository;

@Repository("queuemanagement.HospitalInfoDao")
public class HospitalInfoDao {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public HospitalInfo saveInfo(HospitalInfo info) {
		this.sessionFactory.getCurrentSession().persist(info);
		logger.info("Hospital Info saved successfully, Hospital details=" + info);
		return info;
	}
}
