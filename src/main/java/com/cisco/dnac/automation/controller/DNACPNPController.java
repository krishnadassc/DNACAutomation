package com.cisco.dnac.automation.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.dnac.common.CommonUrl;
import com.cisco.dnac.common.constants.DNACUrl;
import com.cisco.dnac.common.entity.SiteEntity;
import com.cisco.dnac.pnp.entity.DeviceInfo;
import com.cisco.dnac.pnp.service.PnpService;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(CommonUrl.PNP_URL)
public class DNACPNPController {

	//flow
	//1.
	@Autowired
	private PnpService pnpService;
	private Logger logger = Logger.getLogger(DNACPNPController.class);
	@RequestMapping(value = CommonUrl.PNP_AUTOMATE_URL, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String doPNP() {
		try {
			pnpService.onboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = CommonUrl.PNP_DEVICES, method = RequestMethod.GET)
	@ResponseBody	
	public String pnpDevices() {
	    try {
	    	String response = pnpService.pnpDevices();
	    	logger.info(response);
	    	return response;
	    } catch (Exception e) {

	    }
	    return null;
	}
	
	@RequestMapping(value = CommonUrl.PNP_STATUS, method = RequestMethod.GET)
	@ResponseBody	
	public String pnpStatus(@RequestParam String serialNo) {
	    try {
	    	String response = pnpService.pnpStatus(serialNo);
	    	logger.info(response);
	    	return response;
	    } catch (Exception e) {

	    }
	    return null;
	}

	@RequestMapping(value = CommonUrl.PNP_IMPORT, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String pnpImport(@ApiParam @RequestBody String payload) {
	    try {
	    	DeviceInfo deviceinfo = new GsonBuilder().create().fromJson(payload, DeviceInfo.class);
	    	String response = pnpService.pnpImport(deviceinfo);
	    	logger.info(response);
	    	return response;
	    } catch (Exception e) {

	    }
	    return null;
	}

	@RequestMapping(value = CommonUrl.PNP_CLAIM, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String pnpClaim(@ApiParam @RequestBody String payload) {
	    try {
	    	String response = pnpService.pnpClaim(payload);
	    	logger.info(response);
	    	return response;
	    } catch (Exception e) {

	    }
	    return null;
	}
	
	@RequestMapping(value = CommonUrl.PNP_device, method = RequestMethod.GET)
	@ResponseBody
	public String getDevice(@RequestParam String serialno ) {
	    try {
	    	String response = pnpService.getDeviceClaimStatus(serialno);
	    	logger.info(response);
	    	return response;
	    } catch (Exception e) {

	    }
	    return null;
	}
}	
