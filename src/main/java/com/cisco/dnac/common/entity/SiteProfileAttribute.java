package com.cisco.dnac.common.entity;

import java.util.ArrayList;

public class SiteProfileAttribute {
	 private String key;
	 private String value;
	 ArrayList < SiteProfileSubAttribute > attribs = new ArrayList < SiteProfileSubAttribute > ();


	 // Getter Methods 

	 public ArrayList<SiteProfileSubAttribute> getAttribs() {
		return attribs;
	}

	public void setAttribs(ArrayList<SiteProfileSubAttribute> attribs) {
		this.attribs = attribs;
	}

	public String getKey() {
	  return key;
	 }

	 public String getValue() {
	  return value;
	 }

	 // Setter Methods 

	 public void setKey(String key) {
	  this.key = key;
	 }

	 public void setValue(String value) {
	  this.value = value;
	 }
}
