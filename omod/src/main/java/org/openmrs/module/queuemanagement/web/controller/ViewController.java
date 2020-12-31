package org.openmrs.module.queuemanagement.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.queuemanagement.api.HospitalInfoService;
import org.openmrs.module.queuemanagement.model.HospitalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private HospitalInfoService hospitalInfoService;
	
	@RequestMapping("/module/queuemanagement/dashboard")
	public String showDashboard() {
		return "/module/queuemanagement/dashboard";
	}
	
	@RequestMapping(value = "/module/queuemanagement/saveHospitalInfo", method = RequestMethod.POST)
	public String saveDeveloper(@ModelAttribute("developer") HospitalInfo info, BindingResult errors) {
		if (errors.hasErrors()) {
			log.info("Errors" + errors);
		}
		this.hospitalInfoService.saveInfo(info);
		return "redirect:/module/queuemanagement/dashboard.form";
	}
}
