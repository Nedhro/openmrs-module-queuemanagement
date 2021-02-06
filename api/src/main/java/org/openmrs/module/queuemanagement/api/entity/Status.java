package org.openmrs.module.queuemanagement.api.entity;

public enum Status {
	ACTIVE("ACTIVE"), CONSULTED("CONSULTED"), SCHEDULED("SCHEDULED"), WAITING("WAITING"), INACTIVE("INACTIVE");
	
	private final String value;
	
	Status(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
