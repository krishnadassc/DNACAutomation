package com.cisco.dnac.template.entity;

public class TemplateParams {
	public TemplateParams() {
		// TODO Auto-generated constructor stub
	}
	
	public TemplateParams(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	private String key;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String value;
	
}
