package com.cisco.dnac.common.entity;

import java.util.*;

public class DeviceProvisioningInfo extends CustomerFacingService {
	private String networkDeviceId;
	private String siteId;
	private String name;
	private ArrayList<String> targetIdList;
	public String getNetworkDeviceId() {
		return networkDeviceId;
	}
	public void setNetworkDeviceId(String networkDeviceId) {
		this.networkDeviceId = networkDeviceId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getTargetIdList() {
		return targetIdList;
	}
	public void setTargetIdList(ArrayList<String> targetIdList) {
		this.targetIdList = targetIdList;
	}
	
	@Override
	public String toString() {
		return "networkDeviceId=" + this.getNetworkDeviceId() + ",name=" + this.getName() +
				",type=" + this.getType();
	}
	

}
