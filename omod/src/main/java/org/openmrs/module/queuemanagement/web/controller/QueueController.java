package org.openmrs.module.queuemanagement.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.queuemanagement.PatientQueue;
import org.openmrs.module.queuemanagement.api.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class QueueController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private QueueManagementService queueManagementService;
	
	@RequestMapping("/module/queuemanagement/dashboard")
	public String showDashboard(ModelAndView model) throws IOException {
		return "/module/queuemanagement/dashboard";
	}
	
	@RequestMapping(value = "/module/queuemanagement/_allqueues", method = RequestMethod.GET)
	@ResponseBody
	public List<PatientQueue> showAllQueues() throws Exception {
		List<PatientQueue> queues = queueManagementService.getAllQueueId();
		System.out.println(" qqq  :::::::" + queues);
		if (queues.isEmpty()) {
			log.info("No Queue has been created yet...");
		} else {
			return queues;
		}
		
		return queues;
	}
	
	@RequestMapping(value = "/module/queuemanagement/generate", method = RequestMethod.POST)
	public ResponseEntity<?> generate(@RequestBody PatientQueue queue) {
		try {
			if (queue != null) {
				PatientQueue patientQueue = this.queueManagementService.getPatientByIdentifier(queue.getVisitroom(),
				    queue.getIdentifier());
				if (patientQueue == null) {
					this.queueManagementService.save(queue);
					log.info("Queue ::" + queue);
					System.out.println("Queue :: " + queue);
					return new ResponseEntity<PatientQueue>(queue, HttpStatus.CREATED);
				} else {
					queue.setId(patientQueue.getId());
					queue.setToken(patientQueue.getToken());
					queue.setIdentifier(queue.getIdentifier());
					queue.setVisitroom(queue.getVisitroom());
					queue.setStatus(queue.getStatus());
					System.out.println(queue);
					this.queueManagementService.update(queue);
					return new ResponseEntity<HttpStatus>(HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<PatientQueue>(queue, HttpStatus.IM_USED);
		}
	}
	
	@RequestMapping(value = "/module/queuemanagement/updateQueue", method = RequestMethod.PUT)
	public ResponseEntity<?> updateQueue(@RequestParam("identifier") String identifier) {
		PatientQueue queue1 = this.queueManagementService.update(identifier);
		System.out.println("q1   " + queue1);
		return new ResponseEntity<PatientQueue>(queue1, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/module/queuemanagement/queueByVisitroom", method = RequestMethod.GET)
	@ResponseBody
	public List<PatientQueue> getQueueByVisitroom(@RequestParam(value = "visitroom", required = false) String visitroom)
	        throws Exception {
		List<PatientQueue> obs = queueManagementService.getPatientQueueByVisitroom(visitroom);
		if (obs == null) {
			log.info("No Queue data found...");
		} else {
			System.out.println("Queues :: " + obs);
			return obs;
		}
		return obs;
	}
	
	@RequestMapping(value = "/module/queuemanagement/visitrooms", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getVisitrooms() {
		List<Object> rooms = queueManagementService.getAllVisitroom();
		return rooms;
	}
	
	@RequestMapping(value = "/module/queuemanagement/getToken", method = RequestMethod.GET)
	@ResponseBody
	public PatientQueue getPatientToken(@RequestParam(value = "identifier") String identifier) {
		PatientQueue patient = queueManagementService.getPatientByIdentifier(identifier);
		return patient;
	}
}
