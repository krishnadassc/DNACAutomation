package com.cisco.dnac.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.dnac.common.CommonUrl;
import com.cisco.dnac.site.service.SiteService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(CommonUrl.SITE_URL)
public class DNACSiteController {

	@Autowired
	private SiteService siteService;
	
	@RequestMapping("/list")
	public String getAllSites() {
		 return siteService.getAllSites();
	}
}
