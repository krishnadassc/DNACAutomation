package com.cisco.dnac.site.service;

import java.util.List;

import com.cisco.dnac.common.entity.SiteProfileEntity;

public interface SiteService {

	public String getAllSites();
	public SiteProfileEntity getSiteProfileBySiteUuid(String siteUuid);
	public List<String> getAllDeviceIds();
}
