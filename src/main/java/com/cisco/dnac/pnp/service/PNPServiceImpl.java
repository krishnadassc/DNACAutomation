package com.cisco.dnac.pnp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cisco.dnac.common.Util.RestClient;
import com.cisco.dnac.common.constants.DNACUrl;

@Service
public class PNPServiceImpl implements PnpService{

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	
	public String onboard() {
		// TODO
		return null;
	}
	
	public String pnpStatus(String serialNo) {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.PNP_STATUS+serialNo);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}
	
	public String pnpImport(String payload) {
		ResponseEntity<String> response =  restClient.exchange(payload, HttpMethod.POST, DNACUrl.PNP_IMPORT);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}
	
	public String pnpClaim() {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.POST, DNACUrl.PNP_CLAIM);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}
	
	
	
	String PNP_STATUS = "onboarding/pnp-device?serialNumber={}";
	
	String PNP_IMPORT = "onboarding/pnp-device/import";
	
	String PNP_CLAIM = "onboarding/pnp-device/claim";

	public String pnpDevices() {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.PNP_DEVICES);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}

}
