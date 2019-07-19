package com.cisco.dnac.pnp.service;

public interface PnpService {

	public String onboard();
	
	public String pnpStatus(String serialNo);
	
	public String pnpDevices();
	
	public String pnpImport(String payload) ;
	
	public String pnpClaim() ;

}
