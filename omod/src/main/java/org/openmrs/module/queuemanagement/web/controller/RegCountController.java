package org.openmrs.module.queuemanagement.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class RegCountController {
	
	@RequestMapping(value = "/module/queuemanagement/totalRegistration/countByUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> showCount() {
		/* ObjectMapper mapper = new ObjectMapper();
		 ObjectNode objectNode = mapper.createObjectNode();
		 objectNode.put("totalPatient", 20);*/
		Map<String, Object> obj = new LinkedHashMap<String, Object>();
		obj.put("totalPatient", 10);
		obj.put("user", "nidhro");
		return obj;
	}
}
