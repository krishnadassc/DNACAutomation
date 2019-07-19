package com.cisco.dnac.pnp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cisco.dnac.common.Util.RestClient;
import com.cisco.dnac.common.constants.DNACUrl;
import com.cisco.dnac.site.service.SiteService;

@Service
public class PNPServiceImpl implements PnpService{

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	
	@Autowired
	private SiteService siteService;
	/* Steps
	 * 1. get the site info with name
	 * 2. find site template
	 * 3. get template params
	 * 4. Add device
	 * 5. claim device
	 * 
	 */	
	public String onboard() {
		try {
			 List<Map<?, ?>> data = PNPUtil.readObjectsFromCsv(new File("src/main/resources/sample.csv"));
			 for (Map<?, ?> deviceData : data) {
				 String siteId = getSiteId();
				 String configId = gettemplateName(siteId);
				 String params = getTemplate(configId, deviceData);
				 String uuid = addDevice();
				 String status = claimDevice();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String findTemplateName(List<Object> data, templateName) {

	/*    for (attr : data){
	        if ('key' in attr){
	            if attr['key'] == 'day0.templates'{
	                for dev in attr['attribs'] {
	                    for template in dev['attribs'][0]['attribs'][0]['attribs'] {
	                        if template['key'] == 'template.id'{
	                            for templ_attrs in template['attribs'] {
	                                if templ_attrs['key'] == 'template.name' and templ_attrs['value'] == templateName{
	                                    return template['value']
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }*/
	}
	
	public String pnpStatus(String serialNo) {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.PNP_STATUS+serialNo);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}
	
	public String pnpImport(String payload) {
		ResponseEntity<String> response =  restClient.exchange(payload, HttpMethod.POST, DNACUrl.PNP_IMPORT);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}
	
	public String pnpClaim() {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.POST, DNACUrl.PNP_CLAIM);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}
	
	
	
	String PNP_STATUS = "onboarding/pnp-device?serialNumber={}";
	
	String PNP_IMPORT = "onboarding/pnp-device/import";
	
	String PNP_CLAIM = "onboarding/pnp-device/claim";

	public String pnpDevices() {
		ResponseEntity<String> response =  restClient.exchange(null, HttpMethod.GET, DNACUrl.PNP_DEVICES);
		if(response.getStatusCodeValue() == 200)
			return response.getBody();
		return "";
	}

}
