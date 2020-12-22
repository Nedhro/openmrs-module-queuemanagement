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

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class QueueController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private QueueManagementService queueManagementService;
	
	@RequestMapping("/module/queuemanagement/dashboard")
	public String showDashboard() {
		return "/module/queuemanagement/dashboard";
	}
	
	@RequestMapping("/module/queuemanagement/roomWiseQueue")
	public String showQueueRoomWise() {
		return "/module/queuemanagement/roomQueue";
	}
	
	@RequestMapping(value = "/module/queuemanagement/allqueues", method = RequestMethod.GET)
	@ResponseBody
	public List<PatientQueue> showAllQueues() {
		List<PatientQueue> queues = queueManagementService.getAllQueueId();
		System.out.println("All Queues :: " + queues);
		if (queues.isEmpty()) {
			log.info("No Queue has been created yet...");
		} else {
			return queues;
		}
		
		return queues;
	}
	
	@RequestMapping(value = "/module/queuemanagement/generate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> saveQueue(@Valid @RequestBody PatientQueue queue) {
		System.out.println("Submitted Queue :: " + queue.getDateCreated() + " :: " + queue.getVisitroom());
		try {
			PatientQueue patientQueue = this.queueManagementService.getPatientByIdentifier(queue.getVisitroom(),
			    queue.getDateCreated());
			if (patientQueue == null) {
				this.queueManagementService.save(queue);
				log.info("Queue ::" + queue);
				System.out.println("Queue :: " + queue);
				return new ResponseEntity<Object>(queue, HttpStatus.CREATED);
			}
			return new ResponseEntity<Object>(queue, HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			log.error("Runtime error while trying to create new queue", e.getCause());
			return new ResponseEntity<Object>(e.getCause(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/module/queuemanagement/updateQueue", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> updateQueue(@Valid @RequestBody PatientQueue queue) throws ParseException {
		try {
			PatientQueue patientQueue = this.queueManagementService.getPatientByIdentifierAndVisitroom(
			    queue.getIdentifier(), queue.getVisitroom(), queue.getDateCreated());
			if (patientQueue == null) {
				throw new RuntimeException("Queue does not exist");
			}
			this.queueManagementService.update(patientQueue);
			return new ResponseEntity<Object>(patientQueue, HttpStatus.OK);
		}
		catch (RuntimeException e) {
			log.error("Runtime error while trying to undo appointment status", e);
			return new ResponseEntity<Object>(e.getCause(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/module/queuemanagement/queueByVisitroom", method = RequestMethod.GET)
	@ResponseBody
	public List<PatientQueue> getQueueByVisitroom(@RequestParam(value = "visitroom", required = true) String visitroom,
	        @RequestParam(value = "dateCreated", required = true) String dateCreated) throws ParseException {
		List<PatientQueue> obs = queueManagementService.getPatientQueueByVisitroom(visitroom, dateCreated);
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
	public PatientQueue getPatientToken(@RequestParam("identifier") String identifier,
	        @RequestParam(value = "dateCreated") String dateCreated) {
		PatientQueue patient = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateCreated);
			patient = queueManagementService.getPatientByIdentifier(identifier, date);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return patient;
	}
	
}
