package com.cisco.dnac.pnp.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class PNPServiceImpl implements PnpService {

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;

	@Autowired
	private SiteService siteService;

	@Autowired
	private TemplateService templateService;
	private Gson gson = new Gson();
	/*
	 * Steps 1. get the site info with name 2. find site template 3. get template
	 * params 4. Add device 5. claim device
	 * 
	 */

	private String getTemplatePFName(ArrayList<SiteProfileAttribute> profileAttributes, String templateName) {
		boolean isTemplateFound = false;
		for (SiteProfileAttribute sitePfAttribute : profileAttributes) {
			if (sitePfAttribute.getKey() != null && sitePfAttribute.getKey().equals("day0.templates")) {
				if(!isTemplateFound)
					isTemplateFound = getTemplateName(sitePfAttribute, templateName);
			}

		}
		return isTemplateFound ? templateName: null;
	}

	public boolean getTemplateName(SiteProfileAttribute sitePfAttribute, String templateName) {
		if (sitePfAttribute.getKey().equals("template.id")){
                for (SiteProfileAttribute subAttribute : sitePfAttribute.getAttribs()) {
                    if (subAttribute.getKey().equals("template.name") && subAttribute.getValue().equals(templateName)) {
                        return true;
                    }
                }
		}
		if (sitePfAttribute.getAttribs() != null || sitePfAttribute.getAttribs().size()>0) {
			boolean istrue = false;
			for (SiteProfileAttribute subAttribute : sitePfAttribute.getAttribs()) {
				istrue = getTemplateName(subAttribute, templateName);  
				if(istrue) {
					return istrue;
				}
            }
		}
		return false;
		
	}

	public String onboard() {
		try {
			List<Map<String, String>> data = PNPUtil.readObjectsFromCsv(new File("src/main/resources/sample.csv"));
			for (Map<String, String> deviceData : data) {
				String templateName = deviceData.get("siteName");
				SiteEntity siteEntity = siteService.getSiteByGroupHierarchyName(deviceData.get("siteName"));
				SiteProfileEntity siteProfileEntity = siteService.getSiteProfileBySiteUuid(siteEntity.getSiteId());
				String configId = getTemplatePFName(siteProfileEntity.getProfileAttributes(), templateName);
				TemplateDetails templateDetail = templateService.getTemplateByTemplateId(configId);
				List<TemplateParams> templateparams = getTempalateParam(templateDetail, deviceData);
				DeviceInfo deviceInfo = new DeviceInfo();
				
				String uuid = pnpImport(deviceInfo);
				JsonObject obj = new JsonObject();
				obj.addProperty("siteId", siteEntity.getSiteId());
				obj.addProperty("deviceId", uuid);
				obj.addProperty("type", "Default");
				String status = pnpClaim(obj.getAsString());
				if("claimed".equals(status)) {
					System.out.println("success");
				}else {
					System.out.println("failed");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private List<TemplateParams> getTempalateParam(TemplateDetails templateDetail, Map<String, String> data) {
		List<TemplateParams> templateParamList = new ArrayList<TemplateParams>();
		for(TemplateParam templateParam: templateDetail.getTemplateParams()) {
			if(data.get("template_"+templateParam.getKey()) != null) {
				templateParamList.add(new TemplateParams(templateParam.getKey(), data.get("template_"+templateParam.getKey())));
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
		String requestBody = "[" + gson.toJson(payload) + "]";
		ResponseEntity<String> response = restClient.exchange(requestBody, HttpMethod.POST, DNACUrl.PNP_IMPORT);
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

}
