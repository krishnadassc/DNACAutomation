package com.cisco.dnac.site.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cisco.dnac.common.Util.RestClient;
import com.cisco.dnac.common.constants.DNACUrl;

@Service
public class SiteServiceImpl implements SiteService{

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	
	public String getAllSites() {
		// TODO Auto-generated method stub
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.SITE_URL);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}

}
