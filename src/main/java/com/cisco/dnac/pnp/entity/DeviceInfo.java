package com.cisco.dnac.pnp.entity;

import java.util.ArrayList;

import org.springframework.beans.factory.parsing.Location;


public class DeviceInfo {
	 AaaCredentials AaaCredentialsObject;
	 private String addedOn;
	 ArrayList < Object > addnMacAddrs = new ArrayList < Object > ();
	 private String agentType;
	 private String authStatus;
	 private String authenticatedSudiSerialNo;
	 ArrayList < Object > capabilitiesSupported = new ArrayList < Object > ();
	 private String cmState;
	 private String description;
	 ArrayList < Object > deviceSudiSerialNos = new ArrayList < Object > ();
	 private String deviceType;
	 ArrayList < Object > featuresSupported = new ArrayList < Object > ();
	 ArrayList < Object > fileSystemList = new ArrayList < Object > ();
	 private String firstContact;
	 private String hostname;
	 ArrayList < Object > httpHeaders = new ArrayList < Object > ();
	 private String imageFile;
	 private String imageVersion;
	 ArrayList < Object > ipInterfaces = new ArrayList < Object > ();
	 private String lastContact;
	 private String lastSyncTime;
	 private String lastUpdateOn;
	 Location LocationObject;
	 private String macAddress;
	 private String mode;
	 private String name;
	 ArrayList < Object > neighborLinks = new ArrayList < Object > ();
	 private String onbState;
	 private String pid;
	 ArrayList < Object > pnpProfileList = new ArrayList < Object > ();
	 private String populateInventory;
	 ArrayList < Object > preWorkflowCliOuputs = new ArrayList < Object > ();
	 private String projectId;
	 private String projectName;
	 private String reloadRequested;
	 private String serialNumber;
	 private String smartAccountId;
	 private String source;
	 private String stack;
	 StackInfo StackInfoObject;
	 private String state;
	 private String sudiRequired;
	 private String tags;
	 ArrayList < Object > userSudiSerialNos = new ArrayList < Object > ();
	 private String virtualAccountId;
	 private String workflowId;
	 private String workflowName;


	 // Getter Methods 

	 public AaaCredentials getAaaCredentials() {
	  return AaaCredentialsObject;
	 }

	 public String getAddedOn() {
	  return addedOn;
	 }

	 public String getAgentType() {
	  return agentType;
	 }

	 public String getAuthStatus() {
	  return authStatus;
	 }

	 public String getAuthenticatedSudiSerialNo() {
	  return authenticatedSudiSerialNo;
	 }

	 public String getCmState() {
	  return cmState;
	 }

	 public String getDescription() {
	  return description;
	 }

	 public String getDeviceType() {
	  return deviceType;
	 }

	 public String getFirstContact() {
	  return firstContact;
	 }

	 public String getHostname() {
	  return hostname;
	 }

	 public String getImageFile() {
	  return imageFile;
	 }

	 public String getImageVersion() {
	  return imageVersion;
	 }

	 public String getLastContact() {
	  return lastContact;
	 }

	 public String getLastSyncTime() {
	  return lastSyncTime;
	 }

	 public String getLastUpdateOn() {
	  return lastUpdateOn;
	 }

	 public Location getLocation() {
	  return LocationObject;
	 }

	 public String getMacAddress() {
	  return macAddress;
	 }

	 public String getMode() {
	  return mode;
	 }

	 public String getName() {
	  return name;
	 }

	 public String getOnbState() {
	  return onbState;
	 }

	 public String getPid() {
	  return pid;
	 }

	 public String getPopulateInventory() {
	  return populateInventory;
	 }

	 public String getProjectId() {
	  return projectId;
	 }

	 public String getProjectName() {
	  return projectName;
	 }

	 public String getReloadRequested() {
	  return reloadRequested;
	 }

	 public String getSerialNumber() {
	  return serialNumber;
	 }

	 public String getSmartAccountId() {
	  return smartAccountId;
	 }

	 public String getSource() {
	  return source;
	 }

	 public String getStack() {
	  return stack;
	 }

	 public StackInfo getStackInfo() {
	  return StackInfoObject;
	 }

	 public String getState() {
	  return state;
	 }

	 public String getSudiRequired() {
	  return sudiRequired;
	 }

	 public String getTags() {
	  return tags;
	 }

	 public String getVirtualAccountId() {
	  return virtualAccountId;
	 }

	 public String getWorkflowId() {
	  return workflowId;
	 }

	 public String getWorkflowName() {
	  return workflowName;
	 }

	 // Setter Methods 

	 public void setAaaCredentials(AaaCredentials aaaCredentialsObject) {
	  this.AaaCredentialsObject = aaaCredentialsObject;
	 }

	 public void setAddedOn(String addedOn) {
	  this.addedOn = addedOn;
	 }

	 public void setAgentType(String agentType) {
	  this.agentType = agentType;
	 }

	 public void setAuthStatus(String authStatus) {
	  this.authStatus = authStatus;
	 }

	 public void setAuthenticatedSudiSerialNo(String authenticatedSudiSerialNo) {
	  this.authenticatedSudiSerialNo = authenticatedSudiSerialNo;
	 }

	 public void setCmState(String cmState) {
	  this.cmState = cmState;
	 }

	 public void setDescription(String description) {
	  this.description = description;
	 }

	 public void setDeviceType(String deviceType) {
	  this.deviceType = deviceType;
	 }

	 public void setFirstContact(String firstContact) {
	  this.firstContact = firstContact;
	 }

	 public void setHostname(String hostname) {
	  this.hostname = hostname;
	 }

	 public void setImageFile(String imageFile) {
	  this.imageFile = imageFile;
	 }

	 public void setImageVersion(String imageVersion) {
	  this.imageVersion = imageVersion;
	 }

	 public void setLastContact(String lastContact) {
	  this.lastContact = lastContact;
	 }

	 public void setLastSyncTime(String lastSyncTime) {
	  this.lastSyncTime = lastSyncTime;
	 }

	 public void setLastUpdateOn(String lastUpdateOn) {
	  this.lastUpdateOn = lastUpdateOn;
	 }

	 public void setLocation(Location locationObject) {
	  this.LocationObject = locationObject;
	 }

	 public void setMacAddress(String macAddress) {
	  this.macAddress = macAddress;
	 }

	 public void setMode(String mode) {
	  this.mode = mode;
	 }

	 public void setName(String name) {
	  this.name = name;
	 }

	 public void setOnbState(String onbState) {
	  this.onbState = onbState;
	 }

	 public void setPid(String pid) {
	  this.pid = pid;
	 }

	 public void setPopulateInventory(String populateInventory) {
	  this.populateInventory = populateInventory;
	 }

	 public void setProjectId(String projectId) {
	  this.projectId = projectId;
	 }

	 public void setProjectName(String projectName) {
	  this.projectName = projectName;
	 }

	 public void setReloadRequested(String reloadRequested) {
	  this.reloadRequested = reloadRequested;
	 }

	 public void setSerialNumber(String serialNumber) {
	  this.serialNumber = serialNumber;
	 }

	 public void setSmartAccountId(String smartAccountId) {
	  this.smartAccountId = smartAccountId;
	 }

	 public void setSource(String source) {
	  this.source = source;
	 }

	 public void setStack(String stack) {
	  this.stack = stack;
	 }

	 public void setStackInfo(StackInfo stackInfoObject) {
	  this.StackInfoObject = stackInfoObject;
	 }

	 public void setState(String state) {
	  this.state = state;
	 }

	 public void setSudiRequired(String sudiRequired) {
	  this.sudiRequired = sudiRequired;
	 }

	 public void setTags(String tags) {
	  this.tags = tags;
	 }

	 public void setVirtualAccountId(String virtualAccountId) {
	  this.virtualAccountId = virtualAccountId;
	 }

	 public void setWorkflowId(String workflowId) {
	  this.workflowId = workflowId;
	 }

	 public void setWorkflowName(String workflowName) {
	  this.workflowName = workflowName;
	 }
	}
