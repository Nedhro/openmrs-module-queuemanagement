package org.openmrs.module.queuemanagement;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "queuemanagement.PatientQueue")
@Table(name = "queue_v4")
public class PatientQueue implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "queueid", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "token")
	private Integer token;
	
	@Basic(optional = false, fetch = FetchType.LAZY)
	@Column(name = "identifier")
	private String identifier;
	
	@Basic(optional = false, fetch = FetchType.LAZY)
	@Column(name = "visitroom")
	private String visitroom;
	
	@Basic(optional = false, fetch = FetchType.EAGER)
	@Column(name = "room_name")
	private String roomName;
	
	@Basic(optional = false, fetch = FetchType.EAGER)
	@Column(name = "date_created", updatable = true)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date dateCreated;
	
	@Basic(optional = false, fetch = FetchType.EAGER)
	@Column(name = "status", updatable = true)
	private Boolean status;
	
	public PatientQueue() {
	}
	
	public PatientQueue(String identifier, String visitroom, String roomName, Date dateCreated, Boolean status) {
		this.identifier = identifier;
		this.visitroom = visitroom;
		this.roomName = roomName;
		this.dateCreated = dateCreated;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getToken() {
		return token;
	}
	
	public void setToken(Integer token) {
		this.token = token;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getVisitroom() {
		return visitroom;
	}
	
	public void setVisitroom(String visitroom) {
		this.visitroom = visitroom;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "PatientQueue{" + "id=" + id + ", token=" + token + ", identifier='" + identifier + '\'' + ", visitroom='"
		        + visitroom + '\'' + ", roomName='" + roomName + '\'' + ", dateCreated=" + dateCreated + ", status="
		        + status + '}';
	}
}
