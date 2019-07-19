package com.cisco.dnac.pnp.entity;

import java.util.ArrayList;

public class SystemWorkflow {
	 private String _id;
	 private String addToInventory;
	 private String addedOn;
	 private String configId;
	 private String currTaskIdx;
	 private String description;
	 private String endTime;
	 private String execTime;
	 private String imageId;
	 private String instanceType;
	 private String lastupdateOn;
	 private String name;
	 private String startTime;
	 private String state;
	 ArrayList < Object > tasks = new ArrayList < Object > ();
	 private String tenantId;
	 private String type;
	 private String useState;
	 private String version;


	 // Getter Methods 

	 public String get_id() {
	  return _id;
	 }

	 public String getAddToInventory() {
	  return addToInventory;
	 }

	 public String getAddedOn() {
	  return addedOn;
	 }

	 public String getConfigId() {
	  return configId;
	 }

	 public String getCurrTaskIdx() {
	  return currTaskIdx;
	 }

	 public String getDescription() {
	  return description;
	 }

	 public String getEndTime() {
	  return endTime;
	 }

	 public String getExecTime() {
	  return execTime;
	 }

	 public String getImageId() {
	  return imageId;
	 }

	 public String getInstanceType() {
	  return instanceType;
	 }

	 public String getLastupdateOn() {
	  return lastupdateOn;
	 }

	 public String getName() {
	  return name;
	 }

	 public String getStartTime() {
	  return startTime;
	 }

	 public String getState() {
	  return state;
	 }

	 public String getTenantId() {
	  return tenantId;
	 }

	 public String getType() {
	  return type;
	 }

	 public String getUseState() {
	  return useState;
	 }

	 public String getVersion() {
	  return version;
	 }

	 // Setter Methods 

	 public void set_id(String _id) {
	  this._id = _id;
	 }

	 public void setAddToInventory(String addToInventory) {
	  this.addToInventory = addToInventory;
	 }

	 public void setAddedOn(String addedOn) {
	  this.addedOn = addedOn;
	 }

	 public void setConfigId(String configId) {
	  this.configId = configId;
	 }

	 public void setCurrTaskIdx(String currTaskIdx) {
	  this.currTaskIdx = currTaskIdx;
	 }

	 public void setDescription(String description) {
	  this.description = description;
	 }

	 public void setEndTime(String endTime) {
	  this.endTime = endTime;
	 }

	 public void setExecTime(String execTime) {
	  this.execTime = execTime;
	 }

	 public void setImageId(String imageId) {
	  this.imageId = imageId;
	 }

	 public void setInstanceType(String instanceType) {
	  this.instanceType = instanceType;
	 }

	 public void setLastupdateOn(String lastupdateOn) {
	  this.lastupdateOn = lastupdateOn;
	 }

	 public void setName(String name) {
	  this.name = name;
	 }

	 public void setStartTime(String startTime) {
	  this.startTime = startTime;
	 }

	 public void setState(String state) {
	  this.state = state;
	 }

	 public void setTenantId(String tenantId) {
	  this.tenantId = tenantId;
	 }

	 public void setType(String type) {
	  this.type = type;
	 }

	 public void setUseState(String useState) {
	  this.useState = useState;
	 }

	 public void setVersion(String version) {
	  this.version = version;
	 }
	}
