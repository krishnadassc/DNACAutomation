package com.cisco.dnac.common.constants;

public interface DNACUrl {
	
	String SITE_URL = "dna/intent/api/v1/topology/site-topology";
	
	String TEMPLATE_PREVIEW = "template-programmer/template/preview";
	
	String TEMPLATE_GET = "template-programmer/template/{}";
	
	String PNP_GET = "dna/intent/api/v1/onboarding/pnp-device";

	String PNP_STATUS = "dna/intent/api/v1/onboarding/pnp-device?serialNumber=";
	
	String PNP_IMPORT = "dna/intent/api/v1/onboarding/pnp-device/import";
	
	String PNP_CLAIM = "dna/intent/api/v1/onboarding/pnp-device/site-claim";
	
	String PNP_DEVICES = "dna/intent/api/v1/onboarding/pnp-device";
	
	String DEVICE_URL = "dna/intent/api/v1/network-device";
	
	String MEMBER_GROUP_URL= "dna/intent/api/v1/member/group";

}
