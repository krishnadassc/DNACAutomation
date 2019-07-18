package com.cisco.dnac.site.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cisco.dnac.common.Util.RestClient;
import com.cisco.dnac.common.constants.DNACUrl;
import com.cisco.dnac.common.entity.SiteEntity;
import com.cisco.dnac.site.service.SiteService;
import com.cisco.it.sig.common.dao.CommonDaoImpl;
import com.cisco.it.sig.common.dao.SiteProfileDao;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;


@Service
public class SiteServiceImpl implements SiteService{

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	private SiteEntity siteEntity;
	//private SiteProfileDao siteProfileDao = new SiteProfileDao();
	private JSONObject siteResponseJson;
	
	public String getSitesAPIResponse() {
		// TODO Auto-generated method stub
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.SITE_URL);
		if(response.getStatusCodeValue() == 200) {
			return response.getBody();
		} 
		return null;
	}
	
	public String getAllSites() {
		String siteResponse = getSitesAPIResponse();
	    if(!siteResponse.equals(null) || !siteResponse.equals("")) {
	    	return siteResponseJson.getJSONArray("sites").toString();
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
  
	public List<SiteEntity> getSiteHierarchyBySiteId(String parentId) {
		List<SiteEntity> siteEntityList = new ArrayList<>();
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
		List<SiteEntity> siteEntityList = new ArrayList<>();
		String sites = getAllSites(); 	
		SiteEntity[] siteEntities = new GsonBuilder().create().fromJson(sites.toString(), SiteEntity[].class);
		for(SiteEntity siteEnt : siteEntities) {
			if(siteEnt.getGroupNameHierarchy().contains(groupNameHierarchy)) {
				siteEntityList.add(siteEnt);
			}
		}
	    return siteEntityList;
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
