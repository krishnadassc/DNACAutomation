package com.cisco.dnac.template.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.cisco.dnac.common.Util.RestClient;
import com.cisco.dnac.common.constants.DNACUrl;
import com.cisco.dnac.common.entity.DeviceEntity;
import com.cisco.dnac.common.entity.SiteEntity;
import com.cisco.dnac.common.entity.SiteProfileEntity;
import com.cisco.dnac.template.entity.TemplateDetails;
import com.google.gson.GsonBuilder;

@Service
public class TemplateServiceImpl implements TemplateService{

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	private SiteEntity siteEntity;
	//private SiteProfileDao siteProfileDao = new SiteProfileDao();
	public TemplateDetails getTemplateByTemplateId(String templateId) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.TEMPLATE_URL+templateId);
		if(response.getStatusCodeValue() == 200) {
			String temp = response.getBody();
			TemplateDetails templateDetails = new GsonBuilder().create().fromJson(temp, TemplateDetails.class);
			return templateDetails;
		} 
		return null;
	}

	


}
