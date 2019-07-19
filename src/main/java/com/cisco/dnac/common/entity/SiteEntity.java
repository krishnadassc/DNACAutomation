package com.cisco.dnac.common.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

public class SiteEntity {
	private ObjectId id;
	private String siteId;
    private String siteName;
    private String parentId;
    private float latitude;
    private float longitude;
    private String locationType;
    private String locationAddress;
    private String locationCountry;
    private String displayName;
    private String groupNameHierarchy;
   	
	
    public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocationAddress() {
		return locationAddress;
	}
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	public String getLocationCountry() {
		return locationCountry;
	}
	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getGroupNameHierarchy() {
		return groupNameHierarchy;
	}
	public void setGroupNameHierarchy(String groupNameHierarchy) {
		this.groupNameHierarchy = groupNameHierarchy;
	}
}
