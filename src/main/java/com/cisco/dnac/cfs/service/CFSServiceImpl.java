package com.cisco.dnac.cfs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cisco.dnac.common.Util.RestClient;
import com.cisco.dnac.common.constants.DNACUrl;
import com.cisco.dnac.common.entity.DeviceProvisioningInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service	
public class CFSServiceImpl implements CFSService {

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	private Gson gson = new Gson();
	DeviceProvisioningInfo[] targetArray;
	
	public String getAllDeviceInfo() {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.GET_DEVICE_INFO_URL);
		if(response.getStatusCodeValue() == 200) {
			String respjson = response.getBody();
//			System.out.println(respjson);
//		    targetArray = new GsonBuilder().create().fromJson(respjson, DeviceProvisioningInfo[].class);
//			System.out.println(targetArray.length);
//			if(targetArray.length > 0) {
//				for(int i=0; i <targetArray.length;i++ ) {
//					System.out.println(targetArray[i].toString());
//				}
//				
//			}
			return respjson;
		}
		return "";
	}
	//?networkDeviceId=

	public DeviceProvisioningInfo getDeviceInfoByNetworkDeviceId(String networkDeviceId) {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, 
				DNACUrl.GET_DEVICE_INFO_URL + "?networkDeviceId=" + networkDeviceId );
		if(response.getStatusCodeValue() == 200) {
			String resp = response.getBody();
			JsonParser parser= new JsonParser();
			JsonObject element = (JsonObject) parser.parse(resp);
			JsonArray respString = element.get("response").getAsJsonArray();
			System.out.println(respString);
		    targetArray = new GsonBuilder().create().fromJson(respString, DeviceProvisioningInfo[].class);
			System.out.println(targetArray.length);
			if(targetArray.length > 0) 
				return targetArray[0];
		}
			 
		return null;
	}

	public String provisionDevice(DeviceProvisioningInfo deviceInfo) {
		// TODO Auto-generated method stub
		String requestBody = "[" + gson.toJson(deviceInfo) + "]";
		
	
		System.out.println(requestBody);
		MultiValueMap<String, String> headerMap = new LinkedMultiValueMap();
		headerMap.add("Content-Type", "application/json");
		ResponseEntity<String> response = restClient.exchange( requestBody , HttpMethod.POST, DNACUrl.GET_DEVICE_INFO_URL + "?serviceType=DeviceInfo", headerMap);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}
	
	


}
