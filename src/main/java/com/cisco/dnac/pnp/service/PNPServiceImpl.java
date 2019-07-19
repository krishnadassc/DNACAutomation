package com.cisco.dnac.pnp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cisco.dnac.common.Util.RestClient;
import com.cisco.dnac.common.constants.DNACUrl;
import com.cisco.dnac.scheduler.service.WorkflowInvoker;
import com.cisco.dnac.site.service.SiteService;

@Service
public class PNPServiceImpl implements PnpService{

	@Autowired()
	@Qualifier("dnacRestClient")
	private RestClient restClient;
	
	@Autowired
	private SiteService siteService;
	
	private static final Logger logger = Logger.getLogger(WorkflowInvoker.class);
	
	/* Steps
	 * 1. get the site info with name
	 * 2. find site template
	 * 3. get template params
	 * 4. Add device
	 * 5. claim device
	 * 
	 */	
<<<<<<< HEAD
=======
	
	
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
>>>>>>> 5147f10057be144bd119254ea336c01281b61eb4

	
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

	@Override
<<<<<<< HEAD
	public String onboard() {
		// TODO Auto-generated method stub
		return null;
=======
	public void execute() {
		// Needs to Implement the hook ... 
		
		logger.info("Invoked PNP Service ... ");
		
		
>>>>>>> 5147f10057be144bd119254ea336c01281b61eb4
	}

}
