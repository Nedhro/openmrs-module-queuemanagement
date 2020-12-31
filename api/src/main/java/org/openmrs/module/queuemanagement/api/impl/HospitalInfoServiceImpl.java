package org.openmrs.module.queuemanagement.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.queuemanagement.api.HospitalInfoService;
import org.openmrs.module.queuemanagement.api.dao.HospitalInfoDao;
import org.openmrs.module.queuemanagement.model.HospitalInfo;
import org.springframework.stereotype.Service;

@Service
public class HospitalInfoServiceImpl extends BaseOpenmrsService implements HospitalInfoService {
	
	HospitalInfoDao dao;
	
	public void setDao(HospitalInfoDao dao) {
		this.dao = dao;
	}
	
	@Override
	public HospitalInfo saveInfo(HospitalInfo info) throws APIException {
		return dao.saveInfo(info);
	}
}
