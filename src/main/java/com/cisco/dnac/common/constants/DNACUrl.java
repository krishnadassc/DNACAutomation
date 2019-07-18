package com.cisco.dnac.common.constants;

public interface DNACUrl {
	
	String SITE_URL = "dna/intent/api/v1/topology/site-topology";
	
	String TEMPLATE_PREVIEW = "template-programmer/template/preview";
	
	String TEMPLATE_GET = "template-programmer/template/{}";
	
	String PNP_GET = "dna/intent/api/v1/onboarding/pnp-device";

	String PNP_STATUS = "onboarding/pnp-device?serialNumber=";
	
	String PNP_IMPORT = "onboarding/pnp-device/import";
	
	String PNP_CLAIM = "onboarding/pnp-device/claim";
	
	String PNP_DEVICES = "dna/intent/api/v1/onboarding/pnp-device";
	
}
