package org.openmrs.module.queuemanagement.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "queuemanagement.HospitalInfo")
@Table(name = "hospital_info_v1")
public class HospitalInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "hid", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "mobile")
	private String mobile;
	
	public HospitalInfo() {
	}
	
	public HospitalInfo(String name, String address, String email, String mobile) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public String toString() {
		return "HospitalInfo{" + "id=" + id + ", name='" + name + '\'' + ", address='" + address + '\'' + ", email='"
		        + email + '\'' + ", mobile='" + mobile + '\'' + '}';
	}
}
