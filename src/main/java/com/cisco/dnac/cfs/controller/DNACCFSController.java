package com.cisco.dnac.cfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.dnac.cfs.service.CFSService;
import com.cisco.dnac.common.CommonUrl;
import com.cisco.dnac.common.entity.DeviceProvisioningInfo;

@RestController
@RequestMapping(CommonUrl.CFS_URL)
public class DNACCFSController {

	@Autowired
	private CFSService cfsService;
	
	@RequestMapping("/deviceInfoList")
	public String getAllDeviceInfo() {
		 return cfsService.getAllDeviceInfo();
	}
	
	@RequestMapping("/deviceInfo")
	public String getDeviceInfoByNetworkDeviceId(String networkDeviceId) {
		 return cfsService.getDeviceInfoByNetworkDeviceId(networkDeviceId);
	}
	
	@RequestMapping("/provisionDevice")
	public String provisionDevice(DeviceProvisioningInfo deviceInfo) {
		 return cfsService.provisionDevice(deviceInfo);
	}
}

