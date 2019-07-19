package com.cisco.dnac.site.service;

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
import com.google.gson.GsonBuilder;

@Service
public class SiteServiceImpl implements SiteService{

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	private SiteEntity siteEntity;
	//private SiteProfileDao siteProfileDao = new SiteProfileDao();
	
	public String getSitesAPIResponse() {
		// TODO Auto-generated method stub
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.SITE_URL);
		if(response.getStatusCodeValue() == 200) {
			return response.getBody();
		} 
		return null;
	}
	
	public String getAllDevicesAPIResponse() {
		// TODO Auto-generated method stub
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.DEVICE_URL);
		if(response.getStatusCodeValue() == 200) {
			return response.getBody();
		} 
		return null;
	}
	
	public String getAllGroupMembersAPIResponse() {
		// TODO Auto-generated method stub
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.MEMBER_GROUP_URL);
		if(response.getStatusCodeValue() == 200) {
			return response.getBody();
		} 
		return null;
	}
	
	public String getAllSites() {
		String siteResponse = getSitesAPIResponse();
	    if(!siteResponse.equals(null) && !siteResponse.equals("")) {
	    	
	    	return new JSONObject(siteResponse).getJSONObject("response").getJSONArray("sites").toString();
	    }
	    return null;
	}

	public SiteEntity getSiteBySiteId(String siteId) {
		String sites = getAllSites();
		SiteEntity[] siteEntityList = new GsonBuilder().create().fromJson(sites.toString(), SiteEntity[].class);
		for(SiteEntity siteEnt : siteEntityList) {
			if(siteEnt.getSiteId().equals(siteId)) {
				return siteEnt;
			}
		}
	    return null;
	}
	
	public SiteEntity getSiteByGroupHierarchyName(String groupHierarchyName) {
		try {
			String sites = getAllSites();
			System.out.println(sites.toString());
			SiteEntity[] siteEntityList = new GsonBuilder().create().fromJson(sites.toString(), SiteEntity[].class);
			for(SiteEntity siteEnt : siteEntityList) {
				if(siteEnt.getGroupNameHierarchy().equalsIgnoreCase(groupHierarchyName)) {
					return siteEnt;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
  
	public List<SiteEntity> getSiteHierarchyBySiteId(String parentId) {
		List<SiteEntity> siteEntityList = new ArrayList();
		String sites = getAllSites(); 	
		SiteEntity[] siteEntities = new GsonBuilder().create().fromJson(sites.toString(), SiteEntity[].class);
		for(SiteEntity siteEnt : siteEntities) {
			if(siteEnt.getParentId().equals(parentId)) {
				siteEntityList.add(siteEnt);
			}
		}
	    return siteEntityList;
	}
	
	public List<SiteEntity> getSiteHierarchyBySiteHierarchyName(String groupNameHierarchy) {
		List<SiteEntity> siteEntityList = new ArrayList();
		String sites = getAllSites(); 	
		SiteEntity[] siteEntities = new GsonBuilder().create().fromJson(sites.toString(), SiteEntity[].class);
		for(SiteEntity siteEnt : siteEntities) {
			if(siteEnt.getGroupNameHierarchy().contains(groupNameHierarchy)) {
				siteEntityList.add(siteEnt);
			}
		}
	    return siteEntityList;
	}
	
	public List<DeviceEntity> getAllDevicesList() {
		String deviceResponse = getAllDevicesAPIResponse();
	    if(!deviceResponse.equals(null) && !deviceResponse.equals("")) {
	    	JSONArray deviceArray = new JSONObject(deviceResponse).getJSONArray("response");
	    	if(deviceArray != null) {
	    		List<DeviceEntity> deviceEntityList = (List<DeviceEntity>) new GsonBuilder().create().fromJson(deviceArray.toString(), DeviceEntity.class);	    		
                return deviceEntityList;	    	
	    	}
	    }
	    return null;
	}
	
	public List<DeviceEntity> getFilteredDevicesList(List<String> deviceIds) {
		List<DeviceEntity> deviceEntityListFiltered = new ArrayList();
		for(DeviceEntity deviceEntity: getAllDevicesList()) {
			for(String deviceId : deviceIds) {
				if(deviceId.equalsIgnoreCase(deviceEntity.getId())) {
					deviceEntityListFiltered.add(deviceEntity);
				}
			}			
		}
		return deviceEntityListFiltered;
			
	}
	
	public List<String> getAllDeviceIds() {
		List<String> deviceIds = new ArrayList();
		for(DeviceEntity deviceEnt : getAllDevicesList()) {
			deviceIds.add(deviceEnt.getId());
		}
		return deviceIds;
	}
  	
	public List<String> getDeviceIdsBySiteHierarchyName(String groupNameHierarchy) { 
		List<String> deviceList = getAllDeviceIds();
		List<String> deviceIdList = new ArrayList();
		if(deviceList != null && !deviceList.isEmpty()) {
			String deviceIdCommaSeparated = String.join(",", getAllDeviceIds());
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(DNACUrl.MEMBER_GROUP_URL)
	                .queryParam("id", deviceIdCommaSeparated);
			ResponseEntity<String> response =  restClient.exchange(uriBuilder.toUriString(), HttpMethod.GET, DNACUrl.MEMBER_GROUP_URL);
			if(response.getStatusCodeValue() == 200) {
				JSONObject responseJsonObject = new JSONObject(response.getBody()).getJSONObject("response");
				for(String deviceId : deviceList) {
					JSONArray jArray = responseJsonObject.getJSONArray(deviceId);
					if(jArray.length() > 0) {
						JSONObject jObj = jArray.getJSONObject(0);
						if(jObj.getString("groupNameHierarchy").equalsIgnoreCase(groupNameHierarchy)) {
							deviceIdList.add(deviceId);
						}
					}					
				}
			}	
			
		}
		
		return deviceIdList;
		
	}

	public SiteProfileEntity getSiteProfileBySiteUuid(String siteUuid) {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.SITE_PROFILE_URL+"/"+siteUuid);
		if(response.getStatusCodeValue() == 200) {
			JSONArray jArray = new JSONObject(response.getBody()).getJSONArray("response");
			if(jArray.length() > 0) {
				 return new GsonBuilder().create().fromJson(jArray.toString(), SiteProfileEntity.class);
			}					
		}
		return null;
	}	
//    public void saveAllSites() {
//    	String sites = getAllSites();        	
//    	if(!sites.equals(null) || !sites.equals("")) {
//    		JSONObject siteResponseJson = new JSONObject(sites);
//    		JSONArray jsonArray = siteResponseJson.getJSONArray("sites");
//            SiteEntity[] siteEntityList = new GsonBuilder().create().fromJson(jsonArray.toString(), SiteEntity[].class);
//            for(SiteEntity siteEnt : siteEntityList) {
//            	new SiteProfileDao().save(siteEnt);
//            } 
//            System.out.println("Site empty or null");
//    	}
//    }
	
//    public SiteEntity getBySiteId(String siteId) {
 //   	SiteEntity site = siteProfileDao.findBySiteId(siteId);
  //  	return site;
   // }
    
  //  public List<SiteEntity> getSiteHierarchyBySiteId(String parentSiteId) {
   // 	List<SiteEntity> siteEntityList = siteProfileDao.getSiteHierarchyBySiteId(parentSiteId);
   // 	return siteEntityList;
    //}
	
	


}
