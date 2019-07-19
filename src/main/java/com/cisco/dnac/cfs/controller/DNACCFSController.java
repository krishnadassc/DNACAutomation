package com.cisco.dnac.cfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String getDeviceInfoByNetworkDeviceId(@RequestParam String networkDeviceId) {
		DeviceProvisioningInfo deviceInfo= cfsService.getDeviceInfoByNetworkDeviceId(networkDeviceId);
		if(deviceInfo != null)
			return deviceInfo.toString();
		else
			return "";
			
	}
	
	@RequestMapping("/provisionDevice")
	public String provisionDevice(DeviceProvisioningInfo deviceInfo) {
		//This line is for my testing. Instead build the DeviceProvisionigInfo POJO with networkdeviceid and siteid and pass it to this method.
		//sample format.
		//[{"networkDeviceId":"2f794296-5301-4c01-afc7-0e66701b14d5","siteId":"29c1ed83-f8a3-4e51-801b-93f6380cfaee","name":"Edge-4.cisco.com","targetIdList":[],"type":"DeviceInfo"}]
		DeviceProvisioningInfo deviceInfo1= cfsService.getDeviceInfoByNetworkDeviceId("2f794296-5301-4c01-afc7-0e66701b14d5");
		 return cfsService.provisionDevice(deviceInfo1);
	}
}

