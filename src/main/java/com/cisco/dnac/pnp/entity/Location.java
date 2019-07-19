package com.cisco.dnac.pnp.entity;

public class Location {
	 private String address;
	 private String altitude;
	 private String latitude;
	 private String longitude;
	 private String siteId;


	 // Getter Methods 

	 public String getAddress() {
	  return address;
	 }

	 public String getAltitude() {
	  return altitude;
	 }

	 public String getLatitude() {
	  return latitude;
	 }

	 public String getLongitude() {
	  return longitude;
	 }

	 public String getSiteId() {
	  return siteId;
	 }

	 // Setter Methods 

	 public void setAddress(String address) {
	  this.address = address;
	 }

	 public void setAltitude(String altitude) {
	  this.altitude = altitude;
	 }

	 public void setLatitude(String latitude) {
	  this.latitude = latitude;
	 }

	 public void setLongitude(String longitude) {
	  this.longitude = longitude;
	 }

	 public void setSiteId(String siteId) {
	  this.siteId = siteId;
	 }
	}
