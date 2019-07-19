package com.cisco.dnac.site.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.dnac.common.CommonUrl;
import com.cisco.dnac.common.entity.SiteProfileEntity;
import com.cisco.dnac.site.service.SiteService;

@RestController
@RequestMapping(CommonUrl.SITE_URL)
public class DNACSiteController {

	@Autowired
	private SiteService siteService;
	
	@RequestMapping("/list")
	public String getAllSites() {
		 return siteService.getAllSites();
	}
	
	@RequestMapping(CommonUrl.SITE_PROFILE_URL)
	public SiteProfileEntity getSiteProfileBySiteUuid(String siteUuid) {
		return siteService.getSiteProfileBySiteUuid(siteUuid);
				
	}
	
	@RequestMapping(CommonUrl.DEVICE_URL)
	public List<String> getAllDeviceIds() {
		return siteService.getAllDeviceIds();
				
	}
}
