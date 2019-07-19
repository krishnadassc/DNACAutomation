package com.cisco.dnac.pnp.entity;

import java.util.ArrayList;

public class BulkPNP {

	 private String _id;
	 DeviceInfo DeviceInfoObject;
	 ArrayList < Object > runSummaryList = new ArrayList < Object > ();
	 SystemResetWorkflow SystemResetWorkflowObject;
	 SystemWorkflow SystemWorkflowObject;
	 private String tenantId;
	 private String version;
	 Workflow WorkflowObject;
	 WorkflowParameters WorkflowParametersObject;


	 // Getter Methods 

	 public String get_id() {
	  return _id;
	 }

	 public DeviceInfo getDeviceInfo() {
	  return DeviceInfoObject;
	 }

	 public SystemResetWorkflow getSystemResetWorkflow() {
	  return SystemResetWorkflowObject;
	 }

	 public SystemWorkflow getSystemWorkflow() {
	  return SystemWorkflowObject;
	 }

	 public String getTenantId() {
	  return tenantId;
	 }

	 public String getVersion() {
	  return version;
	 }

	 public Workflow getWorkflow() {
	  return WorkflowObject;
	 }

	 public WorkflowParameters getWorkflowParameters() {
	  return WorkflowParametersObject;
	 }

	 // Setter Methods 

	 public void set_id(String _id) {
	  this._id = _id;
	 }

	 public void setDeviceInfo(DeviceInfo deviceInfoObject) {
	  this.DeviceInfoObject = deviceInfoObject;
	 }

	 public void setSystemResetWorkflow(SystemResetWorkflow systemResetWorkflowObject) {
	  this.SystemResetWorkflowObject = systemResetWorkflowObject;
	 }

	 public void setSystemWorkflow(SystemWorkflow systemWorkflowObject) {
	  this.SystemWorkflowObject = systemWorkflowObject;
	 }

	 public void setTenantId(String tenantId) {
	  this.tenantId = tenantId;
	 }

	 public void setVersion(String version) {
	  this.version = version;
	 }

	 public void setWorkflow(Workflow workflowObject) {
	  this.WorkflowObject = workflowObject;
	 }

	 public void setWorkflowParameters(WorkflowParameters workflowParametersObject) {
	  this.WorkflowParametersObject = workflowParametersObject;
	 }
	
}
