package org.openmrs.module.queuemanagement.api;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.queuemanagement.model.HospitalInfo;
import org.springframework.transaction.annotation.Transactional;

public interface HospitalInfoService extends OpenmrsService {
	
	@Transactional
	HospitalInfo saveInfo(HospitalInfo info) throws APIException;
}
