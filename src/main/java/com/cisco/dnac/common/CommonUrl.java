package com.cisco.dnac.common;

public interface CommonUrl {

	String BASE_URL = "/dnac";

	String PLATFORM_URL = BASE_URL + "/platform";

	String PNP_URL = BASE_URL + "/pnp";

	String SCHEDULER = BASE_URL + "/scheduler";

	String PNP_AUTOMATE_URL = BASE_URL + "/onboard";

	String PNP_STATUS = "/status";

	String PNP_DEVICES = "/devices";

	String PNP_IMPORT = "/import";

	String PNP_CLAIM = "/claim";

	String SITE_URL = "/site";
	
	String SITE_PROFILE_URL = BASE_URL+"/siteprofile/site";
	
	String DEVICE_URL = BASE_URL+"/network-device";

	String SCHEDULER_URL = "/schedule";

	String CFS_URL = BASE_URL + "/cfs";

}
