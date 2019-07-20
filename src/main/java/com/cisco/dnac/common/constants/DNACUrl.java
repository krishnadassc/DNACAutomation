package com.cisco.dnac.common.constants;

public interface DNACUrl {
	
	String SITE_URL = "dna/intent/api/v1/topology/site-topology";

	String GET_DEVICE_INFO_URL = "api/v2/data/customer-facing-service/DeviceInfo";
	
	String TEMPLATE_PREVIEW = "template-programmer/template/preview";
	
	String TEMPLATE_GET = "template-programmer/template/{}";
	
	String PNP_GET = "dna/intent/api/v1/onboarding/pnp-device";

	String PNP_STATUS = "dna/intent/api/v1/onboarding/pnp-device?serialNumber=";
	
	String PNP_IMPORT = "dna/intent/api/v1/onboarding/pnp-device/import";
	
	String PNP_CLAIM = "dna/intent/api/v1/onboarding/pnp-device/site-claim";
	
	String PNP_DEVICES = "dna/intent/api/v1/onboarding/pnp-device";
	
	String DEVICE_URL = "dna/intent/api/v1/network-device";
	
	String MEMBER_GROUP_URL= "dna/intent/api/v1/member/group";
	
	String SITE_PROFILE_URL= "api/v1/siteprofile/site";
	
	String TEMPLATE_URL= "dna/intent/api/v1/template-programmer/template/";
	
	String DEVICE_CLAIM_STATUS = "dna/intent/api/v1/onboarding/pnp-device/";
	
	String NETWORK_DEVICE = "api/v1/network-device";
}
