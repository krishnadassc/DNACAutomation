package com.cisco.dnac.template.entity;

import java.util.ArrayList;

public class TemplateParam {
	 private String binding;
	 private String dataType;
	 private String defaultValue;
	 private String description;
	 private String displayName;
	 private String group;
	 private String id;
	 private String instructionText;
	 private String key;
	 private String notParam;
	 private String order;
	 private String paramArray;
	 private String parameterName;
	 private String provider;
	 ArrayList < Object > range = new ArrayList < Object > ();
	 private String required;
	 Selection SelectionObject;


	 // Getter Methods 

	 public String getBinding() {
	  return binding;
	 }

	 public String getDataType() {
	  return dataType;
	 }

	 public String getDefaultValue() {
	  return defaultValue;
	 }

	 public String getDescription() {
	  return description;
	 }

	 public String getDisplayName() {
	  return displayName;
	 }

	 public String getGroup() {
	  return group;
	 }

	 public String getId() {
	  return id;
	 }

	 public String getInstructionText() {
	  return instructionText;
	 }

	 public String getKey() {
	  return key;
	 }

	 public String getNotParam() {
	  return notParam;
	 }

	 public String getOrder() {
	  return order;
	 }

	 public String getParamArray() {
	  return paramArray;
	 }

	 public String getParameterName() {
	  return parameterName;
	 }

	 public String getProvider() {
	  return provider;
	 }

	 public String getRequired() {
	  return required;
	 }

	 public Selection getSelection() {
	  return SelectionObject;
	 }

	 // Setter Methods 

	 public void setBinding(String binding) {
	  this.binding = binding;
	 }

	 public void setDataType(String dataType) {
	  this.dataType = dataType;
	 }

	 public void setDefaultValue(String defaultValue) {
	  this.defaultValue = defaultValue;
	 }

	 public void setDescription(String description) {
	  this.description = description;
	 }

	 public void setDisplayName(String displayName) {
	  this.displayName = displayName;
	 }

	 public void setGroup(String group) {
	  this.group = group;
	 }

	 public void setId(String id) {
	  this.id = id;
	 }

	 public void setInstructionText(String instructionText) {
	  this.instructionText = instructionText;
	 }

	 public void setKey(String key) {
	  this.key = key;
	 }

	 public void setNotParam(String notParam) {
	  this.notParam = notParam;
	 }

	 public void setOrder(String order) {
	  this.order = order;
	 }

	 public void setParamArray(String paramArray) {
	  this.paramArray = paramArray;
	 }

	 public void setParameterName(String parameterName) {
	  this.parameterName = parameterName;
	 }

	 public void setProvider(String provider) {
	  this.provider = provider;
	 }

	 public void setRequired(String required) {
	  this.required = required;
	 }

	 public void setSelection(Selection selectionObject) {
	  this.SelectionObject = selectionObject;
	 }
}
