package com.cisco.dnac.scheduler.dto;

public class TaskParameters {

	String name;
	String type;
	String value;
	Object objectValue;
	
	public TaskParameters() {
		
	}
	
	public TaskParameters(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value; 
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public Object getObjectValue() {
		return objectValue;
	}
	
	public void setObjectValue(Object object) {
		this.objectValue = object;
	}
	
	
}
