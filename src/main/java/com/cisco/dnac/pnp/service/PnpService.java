package com.cisco.dnac.pnp.service;

import com.cisco.dnac.pnp.entity.DeviceInfo;

public interface PnpService {

	public String onboard();
	
	public String pnpStatus(String serialNo);
	
	public String pnpDevices();
	
	public String pnpImport(DeviceInfo payload);
	
	public String pnpClaim(String payload);

}
