package com.cisco.dnac.template.entity;

import java.util.ArrayList;

public class TemplateDetails {
	 private String author;
	 private String composite;
	 ArrayList < Object > containingTemplates = new ArrayList < Object > ();
	 private String createTime;
	 private String description;
	 ArrayList < Object > deviceTypes = new ArrayList < Object > ();
	 private String failurePolicy;
	 private String id;
	 private String lastUpdateTime;
	 private String name;
	 private String parentTemplateId;
	 private String projectId;
	 private String projectName;
	 private String rollbackTemplateContent;
	 ArrayList < Object > rollbackTemplateParams = new ArrayList < Object > ();
	 private String softwareType;
	 private String softwareVariant;
	 private String softwareVersion;
	 ArrayList < Object > tags = new ArrayList < Object > ();
	 private String templateContent;
	 ArrayList < TemplateParam > templateParams = new ArrayList < TemplateParam > ();
	 private String version;


	 // Getter Methods 

	 public ArrayList<TemplateParam> getTemplateParams() {
		return templateParams;
	}

	public void setTemplateParams(ArrayList<TemplateParam> templateParams) {
		this.templateParams = templateParams;
	}

	public String getAuthor() {
	  return author;
	 }

	 public String getComposite() {
	  return composite;
	 }

	 public String getCreateTime() {
	  return createTime;
	 }

	 public String getDescription() {
	  return description;
	 }

	 public String getFailurePolicy() {
	  return failurePolicy;
	 }

	 public String getId() {
	  return id;
	 }

	 public String getLastUpdateTime() {
	  return lastUpdateTime;
	 }

	 public String getName() {
	  return name;
	 }

	 public String getParentTemplateId() {
	  return parentTemplateId;
	 }

	 public String getProjectId() {
	  return projectId;
	 }

	 public String getProjectName() {
	  return projectName;
	 }

	 public String getRollbackTemplateContent() {
	  return rollbackTemplateContent;
	 }

	 public String getSoftwareType() {
	  return softwareType;
	 }

	 public String getSoftwareVariant() {
	  return softwareVariant;
	 }

	 public String getSoftwareVersion() {
	  return softwareVersion;
	 }

	 public String getTemplateContent() {
	  return templateContent;
	 }

	 public String getVersion() {
	  return version;
	 }

	 // Setter Methods 

	 public void setAuthor(String author) {
	  this.author = author;
	 }

	 public void setComposite(String composite) {
	  this.composite = composite;
	 }

	 public void setCreateTime(String createTime) {
	  this.createTime = createTime;
	 }

	 public void setDescription(String description) {
	  this.description = description;
	 }

	 public void setFailurePolicy(String failurePolicy) {
	  this.failurePolicy = failurePolicy;
	 }

	 public void setId(String id) {
	  this.id = id;
	 }

	 public void setLastUpdateTime(String lastUpdateTime) {
	  this.lastUpdateTime = lastUpdateTime;
	 }

	 public void setName(String name) {
	  this.name = name;
	 }

	 public void setParentTemplateId(String parentTemplateId) {
	  this.parentTemplateId = parentTemplateId;
	 }

	 public void setProjectId(String projectId) {
	  this.projectId = projectId;
	 }

	 public void setProjectName(String projectName) {
	  this.projectName = projectName;
	 }

	 public void setRollbackTemplateContent(String rollbackTemplateContent) {
	  this.rollbackTemplateContent = rollbackTemplateContent;
	 }

	 public void setSoftwareType(String softwareType) {
	  this.softwareType = softwareType;
	 }

	 public void setSoftwareVariant(String softwareVariant) {
	  this.softwareVariant = softwareVariant;
	 }

	 public void setSoftwareVersion(String softwareVersion) {
	  this.softwareVersion = softwareVersion;
	 }

	 public void setTemplateContent(String templateContent) {
	  this.templateContent = templateContent;
	 }

	 public void setVersion(String version) {
	  this.version = version;
	 }
}
