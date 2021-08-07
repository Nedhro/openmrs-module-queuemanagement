package org.openmrs.module.queuemanagement.web.controller;

import org.openmrs.module.queuemanagement.api.entity.PatientQueue;
import org.openmrs.module.queuemanagement.api.entity.Status;
import org.openmrs.module.queuemanagement.api.service.QueueManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class QueueController {

	protected final Logger log = LoggerFactory.getLogger(QueueController.class);

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
		List<PatientQueue> queues = this.queueManagementService.getAllQueueId();
		log.info("All Queues :: " + queues);
		if (queues.isEmpty()) {
			log.info("No Queue has been created yet...");
		} else {
			return queues;
		}

		return queues;
	}

	@RequestMapping(value = "/module/queuemanagement/generate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> saveQueue(@Valid @RequestBody PatientQueue queue) throws IOException {
		log.info("Submitted Queue :: " + queue.getDateCreated() + " :: " + queue.getVisitroom());
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(dateFormat.format(queue.getDateCreated()));
			log.info("Date New :: " + date);
			PatientQueue patientQueue = this.queueManagementService.getPatientByIdentifierAndVisitroom(
			    queue.getIdentifier(), queue.getRoomId(), date);
			log.info("Patient Queue Exists ::" + patientQueue);
			if (patientQueue == null) {
				queue.setStatus(Status.ACTIVE.getValue());
				this.queueManagementService.save(queue);
				log.info("Queue ::" + queue);
				return new ResponseEntity<Object>(queue, HttpStatus.CREATED);
			}
			return new ResponseEntity<Object>(queue, HttpStatus.IM_USED);
		}
		catch (Exception e) {
			log.error("Runtime error while trying to create new queue", e.getCause());
			return new ResponseEntity<Object>(e.getCause(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/module/queuemanagement/updateQueue", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> updateQueue(@RequestParam(value = "identifier") String identifier,
	        @RequestParam(value = "roomId") String roomId) throws Exception {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(dateFormat.format(new Date()));
			log.info("Date New :: " + date);
			PatientQueue patientQueue = this.queueManagementService.getPatientByIdentifierAndVisitroom(identifier, roomId,
			    date);
			if (patientQueue != null) {
				if (patientQueue.getStatus() != Status.CONSULTED.getValue()) {
					patientQueue.setStatus(Status.CONSULTED.getValue());
					this.queueManagementService.update(patientQueue);
					return new ResponseEntity<Object>(patientQueue, HttpStatus.ACCEPTED);
				}

				this.queueManagementService.update(patientQueue);
				return new ResponseEntity<Object>(patientQueue, HttpStatus.ALREADY_REPORTED);
			}
			throw new RuntimeException("Queue does not exist");

		}
		catch (RuntimeException e) {
			log.error("Runtime error while trying to update Queue", e);
			return new ResponseEntity<Object>(e.getCause(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/module/queuemanagement/reconsult", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> reconsult(@RequestParam(value = "identifier") String identifier,
	        @RequestParam(value = "roomId") String roomId) throws Exception {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(dateFormat.format(new Date()));
			log.info("Date New :: " + date);
			PatientQueue patientQueue = this.queueManagementService.getPatientByIdentifierAndVisitroom(identifier, roomId,
			    date);
			if (patientQueue != null) {
				if (patientQueue.getStatus() != Status.ACTIVE.getValue()) {
					patientQueue.setStatus(Status.ACTIVE.getValue());
					this.queueManagementService.update(patientQueue);
					return new ResponseEntity<Object>(patientQueue, HttpStatus.ACCEPTED);
				}

				this.queueManagementService.update(patientQueue);
				return new ResponseEntity<Object>(patientQueue, HttpStatus.ALREADY_REPORTED);
			}
			throw new RuntimeException("Queue does not exist");

		}
		catch (RuntimeException e) {
			log.error("Runtime error while trying to update Queue", e);
			return new ResponseEntity<Object>(e.getCause(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/module/queuemanagement/queueByVisitroom", method = RequestMethod.GET)
	@ResponseBody
	public List<PatientQueue> getQueueByVisitroom(@RequestParam(value = "visitroom") String visitroom) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		log.info("Date New :: " + date);
		List<PatientQueue> obs = this.queueManagementService.getPatientQueueByVisitroom(visitroom, date);
		if (obs == null) {
			log.info("No Queue data found...");
		} else {
			log.info("Queues last six :: " + obs);
			return obs;
		}
		return obs;
	}

	@RequestMapping(value = "/module/queuemanagement/visitrooms", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getVisitrooms() throws ParseException {
		return this.queueManagementService.getAllVisitroom();
	}

	@RequestMapping(value = "/module/queuemanagement/hospitalData", method = RequestMethod.GET)
	@ResponseBody
	public String getHospitalData() {
		return this.queueManagementService.getHospitalData();
	}

	@RequestMapping(value = "/module/queuemanagement/getToken", method = RequestMethod.GET)
	@ResponseBody
	public PatientQueue getPatientToken(@RequestParam("identifier") String identifier,
	        @RequestParam(value = "dateCreated") String dateCreated) {
		PatientQueue patient;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateCreated);
			patient = this.queueManagementService.getTokenByIdentifier(identifier, date);
			return patient;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
