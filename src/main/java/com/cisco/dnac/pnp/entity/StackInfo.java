package com.cisco.dnac.pnp.entity;

import java.util.ArrayList;

public class StackInfo {
	 private String isFullRing;
	 ArrayList < Object > stackMemberList = new ArrayList < Object > ();
	 private String stackRingProtocol;
	 private String supportsStackWorkflows;
	 private String totalMemberCount;
	 ArrayList < Object > validLicenseLevels = new ArrayList < Object > ();


	 // Getter Methods 

	 public String getIsFullRing() {
	  return isFullRing;
	 }

	 public String getStackRingProtocol() {
	  return stackRingProtocol;
	 }

	 public String getSupportsStackWorkflows() {
	  return supportsStackWorkflows;
	 }

	 public String getTotalMemberCount() {
	  return totalMemberCount;
	 }

	 // Setter Methods 

	 public void setIsFullRing(String isFullRing) {
	  this.isFullRing = isFullRing;
	 }

	 public void setStackRingProtocol(String stackRingProtocol) {
	  this.stackRingProtocol = stackRingProtocol;
	 }

	 public void setSupportsStackWorkflows(String supportsStackWorkflows) {
	  this.supportsStackWorkflows = supportsStackWorkflows;
	 }

	 public void setTotalMemberCount(String totalMemberCount) {
	  this.totalMemberCount = totalMemberCount;
	 }
	}
