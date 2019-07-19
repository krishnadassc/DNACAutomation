package com.cisco.dnac.template.entity;

public class Selection {
	 private String id;
	 private String selectionType;
	 private Object SelectionValuesObject;


	 // Getter Methods 

	 public String getId() {
	  return id;
	 }

	 public String getSelectionType() {
	  return selectionType;
	 }

	 public Object getSelectionValues() {
	  return SelectionValuesObject;
	 }

	 // Setter Methods 

	 public void setId(String id) {
	  this.id = id;
	 }

	 public void setSelectionType(String selectionType) {
	  this.selectionType = selectionType;
	 }

	 public void setSelectionValues(Object selectionValuesObject) {
	  this.SelectionValuesObject = selectionValuesObject;
	 }
}
