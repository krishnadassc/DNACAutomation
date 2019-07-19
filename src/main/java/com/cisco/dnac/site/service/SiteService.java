package com.cisco.dnac.site.service;

import java.util.List;


import com.cisco.dnac.common.entity.DeviceEntity;
import com.cisco.dnac.common.entity.SiteEntity;
import com.cisco.dnac.common.entity.SiteProfileEntity;

public interface SiteService {

	
	public String getSitesAPIResponse() ;
	
	public String getAllDevicesAPIResponse() ;
	
	public String getAllGroupMembersAPIResponse();
	
	public String getAllSites() ;

	public SiteEntity getSiteBySiteId(String siteId);
	
	public SiteEntity getSiteByGroupHierarchyName(String groupHierarchyName) ;
  
	public List<SiteEntity> getSiteHierarchyBySiteId(String parentId) ;
	
	public List<SiteEntity> getSiteHierarchyBySiteHierarchyName(String groupNameHierarchy);
	
	public List<DeviceEntity> getAllDevicesList() ;
	
	public List<DeviceEntity> getFilteredDevicesList(List<String> deviceIds);
	
	public List<String> getAllDeviceIds() ;
  	
	public List<String> getDeviceIdsBySiteHierarchyName(String groupNameHierarchy);

	public SiteProfileEntity getSiteProfileBySiteUuid(String siteUuid);


}
