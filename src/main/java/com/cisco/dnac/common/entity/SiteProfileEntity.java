package com.cisco.dnac.common.entity;

import java.util.ArrayList;

public class SiteProfileEntity {
	 private String siteProfileUuid;
	 private float version;
	 private String name;
	 private String namespace;
	 private String status;
	 private String lastUpdatedBy;
	 private float lastUpdatedDatetime;
	 ArrayList < SiteProfileAttribute > profileAttributes = new ArrayList < SiteProfileAttribute > ();


	 // Getter Methods 

	 public ArrayList<SiteProfileAttribute> getProfileAttributes() {
		return profileAttributes;
	}

	public void setProfileAttributes(ArrayList<SiteProfileAttribute> profileAttributes) {
		this.profileAttributes = profileAttributes;
	}

	public String getSiteProfileUuid() {
	  return siteProfileUuid;
	 }

	 public float getVersion() {
	  return version;
	 }

	 public String getName() {
	  return name;
	 }

	 public String getNamespace() {
	  return namespace;
	 }

	 public String getStatus() {
	  return status;
	 }

	 public String getLastUpdatedBy() {
	  return lastUpdatedBy;
	 }

	 public float getLastUpdatedDatetime() {
	  return lastUpdatedDatetime;
	 }

	 // Setter Methods 

	 public void setSiteProfileUuid(String siteProfileUuid) {
	  this.siteProfileUuid = siteProfileUuid;
	 }

	 public void setVersion(float version) {
	  this.version = version;
	 }

	 public void setName(String name) {
	  this.name = name;
	 }

	 public void setNamespace(String namespace) {
	  this.namespace = namespace;
	 }

	 public void setStatus(String status) {
	  this.status = status;
	 }

	 public void setLastUpdatedBy(String lastUpdatedBy) {
	  this.lastUpdatedBy = lastUpdatedBy;
	 }

	 public void setLastUpdatedDatetime(float lastUpdatedDatetime) {
	  this.lastUpdatedDatetime = lastUpdatedDatetime;
	 }
}
