package com.cisco.dnac.common.entity;

import java.util.ArrayList;

public class SiteProfileAttribute {
	 private String key;
	 private String value;
	 ArrayList < SiteProfileAttribute > attribs = new ArrayList < SiteProfileAttribute > ();


	 // Getter Methods 

	 public ArrayList<SiteProfileAttribute> getAttribs() {
		return attribs;
	}

	public void setAttribs(ArrayList<SiteProfileAttribute> attribs) {
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
