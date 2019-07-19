package com.cisco.dnac.pnp.entity;

import java.util.ArrayList;

public class WorkflowParameters {
	 ArrayList < Object > configList = new ArrayList < Object > ();
	 private String licenseLevel;
	 private String licenseType;
	 private String topOfStackSerialNumber;


	 // Getter Methods 

	 public String getLicenseLevel() {
	  return licenseLevel;
	 }

	 public String getLicenseType() {
	  return licenseType;
	 }

	 public String getTopOfStackSerialNumber() {
	  return topOfStackSerialNumber;
	 }

	 // Setter Methods 

	 public void setLicenseLevel(String licenseLevel) {
	  this.licenseLevel = licenseLevel;
	 }

	 public void setLicenseType(String licenseType) {
	  this.licenseType = licenseType;
	 }

	 public void setTopOfStackSerialNumber(String topOfStackSerialNumber) {
	  this.topOfStackSerialNumber = topOfStackSerialNumber;
	 }
	}
