package org.openmrs.module.queuemanagement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "queuemanagement.PatientQueue")
@Table(name = "queue2")
@JsonIgnoreProperties(ignoreUnknown = true)
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
	@Column(name = "status")
	private Boolean status;
	
	public PatientQueue() {
	}
	
	public PatientQueue(Integer token, String identifier, String visitroom) {
		this.token = token;
		this.identifier = identifier;
		this.visitroom = visitroom;
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
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "PatientQueue{" + "id=" + id + ", token=" + token + ", identifier='" + identifier + '\'' + ", visitroom='"
		        + visitroom + '\'' + ", status=" + status + '}';
	}
	
}
