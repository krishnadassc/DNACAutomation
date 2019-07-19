package com.cisco.dnac.cfs.service;

import com.cisco.dnac.common.entity.DeviceProvisioningInfo;

public interface CFSService {
	
	String getAllDeviceInfo();
	String getDeviceInfoByNetworkDeviceId(String networkDeviceId);
	String provisionDevice(DeviceProvisioningInfo deviceInfo);

}
