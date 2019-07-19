package com.cisco.dnac.pnp.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.cisco.dnac.common.entity.SiteProfileAttribute;
import com.cisco.dnac.common.entity.SiteProfileEntity;
import com.cisco.dnac.pnp.entity.DeviceInfo;
import com.cisco.dnac.site.service.SiteService;
import com.cisco.dnac.template.entity.TemplateDetails;
import com.cisco.dnac.template.entity.TemplateParam;
import com.cisco.dnac.template.entity.TemplateParams;
import com.cisco.dnac.template.service.TemplateService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service

public class PNPServiceImpl implements PnpService {

	@Autowired()

	@Qualifier("dnacRestClient")

	private RestClient restClient;

	@Autowired

	private SiteService siteService;

	private Logger logger = Logger.getLogger(PNPServiceImpl.class);

	@Autowired

	private TemplateService templateService;

	private Gson gson = new Gson();

	/*
	 * 
	 * Steps 1. get the site info with name 2. find site template 3. get template
	 * 
	 * params 4. Add device 5. claim device
	 * 
	 * 
	 * 
	 */

	private String getTemplatePFName(ArrayList<SiteProfileAttribute> profileAttributes, String templateName) {

		boolean isTemplateFound = false;

		for (SiteProfileAttribute sitePfAttribute : profileAttributes) {

			if (sitePfAttribute.getKey() != null && sitePfAttribute.getKey().equals("day0.templates")) {

				if (!isTemplateFound)

					isTemplateFound = getTemplateName(sitePfAttribute, templateName);

			}

		}

		return isTemplateFound ? templateName : null;

	}

	public boolean getTemplateName(SiteProfileAttribute sitePfAttribute, String templateName) {

		if (sitePfAttribute.getKey().equals("template.id")) {

			for (SiteProfileAttribute subAttribute : sitePfAttribute.getAttribs()) {

				if (subAttribute.getKey().equals("template.name") && subAttribute.getValue().equals(templateName)) {

					return true;

				}

			}

		}

		if (sitePfAttribute.getAttribs() != null || sitePfAttribute.getAttribs().size() > 0) {

			boolean istrue = false;

			for (SiteProfileAttribute subAttribute : sitePfAttribute.getAttribs()) {

				istrue = getTemplateName(subAttribute, templateName);

				if (istrue) {

					return istrue;

				}

			}

		}

		return false;

	}

	public String getDeviceClaimStatus(String serialNumber) {
		// TODO Auto-generated method stub
		//ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.DEVICE_CLAIM_STATUS+deviceId);
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.NETWORK_DEVICE+"?serialNumber="+serialNumber);
		if(response.getStatusCodeValue() == 200) {
			
			JSONObject jObj= new JSONObject(response.getBody()).getJSONArray("response").getJSONObject(0);
			String instanceuuid = jObj.getString("instanceUuid");
			return instanceuuid;	
		} 
		return null;
	}
	
	public String onboard() {

		List<DeviceInfo> devIndoList = new ArrayList<>();
		List<DeviceInfo> devFailList = new ArrayList<>();
		try {

			List<Map<String, String>> data = PNPUtil.readObjectsFromCsv(new File("src/main/resources/sample.csv"));

			for (Map<String, String> deviceData : data) {

				String templateName = deviceData.get("siteName");

				SiteEntity siteEntity = siteService.getSiteByGroupHierarchyName(deviceData.get("siteName"));

				SiteProfileEntity siteProfileEntity = siteService.getSiteProfileBySiteUuid(siteEntity.getId());
				if(siteProfileEntity != null) {
					String configId = getTemplatePFName(siteProfileEntity.getProfileAttributes(), templateName);
					
					TemplateDetails templateDetail = templateService.getTemplateByTemplateId(configId);
					
					List<TemplateParams> templateparams = getTempalateParam(templateDetail, deviceData);
					
				}

				DeviceInfo deviceInfo = new DeviceInfo();
				deviceInfo.setSerialNumber(deviceData.get("serial"));
				deviceInfo.setName(deviceData.get("name"));
				deviceInfo.setPid(deviceData.get("pid"));
				deviceInfo.setHostname(deviceData.get("hostname"));
				String resp = pnpImport(deviceInfo);
				ObjectMapper mapper1 = new ObjectMapper();
				ObjectNode node1 = null;
				try {
					node1 = mapper1.readValue(resp, ObjectNode.class);
				}catch (Exception e) {
					e.printStackTrace();
				}
				ArrayNode successArr = (ArrayNode) node1.get("successList");
				if(successArr.size()>0) {
					String uuid = successArr.get(0).get("id").toString();
					System.out.println("---------------------"+uuid);
					JsonObject obj = new JsonObject();

					obj.addProperty("siteId", siteEntity.getId());

					obj.addProperty("deviceId", uuid);

					obj.addProperty("type", "Default");

					String status = pnpClaim(obj.getAsString());

					if ("Device claimed".equals(status)) {
						devIndoList.add(deviceInfo);
						
					} else {
						devFailList.add(deviceInfo);
						System.out.println("failed");

					}

					
				}
			}
			
		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		doDeviceprov(devIndoList);
		return null;

	}
	
	public void doDeviceprov(List<DeviceInfo> devIndoList) {
		try {
	        while (devIndoList.size()>0) {
	        	for(DeviceInfo info: devIndoList) {
		            if(getDeviceClaimStatus(info.getSerialNumber()).equals("")) {
		            	devIndoList.remove(info);
		            	//doProv();
		            }
	        	}

	            Thread.sleep(10 * 1000);
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	}

	private List<TemplateParams> getTempalateParam(TemplateDetails templateDetail, Map<String, String> data) {

		List<TemplateParams> templateParamList = new ArrayList<TemplateParams>();

		for (TemplateParam templateParam : templateDetail.getTemplateParams()) {

			if (data.get("template_" + templateParam.getKey()) != null) {

				templateParamList.add(
						new TemplateParams(templateParam.getKey(), data.get("template_" + templateParam.getKey())));

			}

		}

		return templateParamList;

	}

	public String pnpStatus(String serialNo) {

		ResponseEntity<String> response = restClient.exchange(null, HttpMethod.GET, DNACUrl.PNP_STATUS + serialNo);

		if (response.getStatusCodeValue() == 200)

			return response.getBody();

		return "";

	}

	public String pnpImport(DeviceInfo payload) {
		String json= "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectMapper mapper1 = new ObjectMapper();
		ObjectNode node1 = null;
		try {
			node1 = mapper1.readValue(json, ObjectNode.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectNode node = mapper.createObjectNode();
		
		node.set("deviceInfo", node1);
		ObjectMapper mapper2 = new ObjectMapper();
		ArrayNode arr = mapper2.createArrayNode();
		arr.add(node);
		System.out.println(arr.toString());
		System.out.println(arr.textValue());
		System.out.println( arr.asText());
		ResponseEntity<String> response = restClient.exchange(arr.toString(), HttpMethod.POST, DNACUrl.PNP_IMPORT);

		if (response.getStatusCodeValue() == 200)

			return response.getBody();

		return "";

	}

	public String pnpClaim(String payload) {

		ResponseEntity<String> response = restClient.exchange(payload, HttpMethod.POST, DNACUrl.PNP_CLAIM);

		if (response.getStatusCodeValue() == 200)

			return response.getBody();

		return "";

	}

	String PNP_STATUS = "dna/intent/api/v1/onboarding/pnp-device?serialNumber={}";

	String PNP_IMPORT = "dna/intent/api/v1/onboarding/pnp-device/import";

	String PNP_CLAIM = "dna/intent/api/v1/onboarding/pnp-device/site-claim";

	public String pnpDevices() {

		ResponseEntity<String> response = restClient.exchange(null, HttpMethod.GET, DNACUrl.PNP_DEVICES);

		if (response.getStatusCodeValue() == 200)

			return response.getBody();

		return "";

	}

	public void execute() {
		onboard();
		logger.info("Invoked PNP Service ... ");

	}

}
