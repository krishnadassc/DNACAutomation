package com.cisco.dnac.common.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVtoPnPDeviceInfoList {
	
	public static String DEFAULT_DELIM = ",";
	
	private String fileToParse;
	private String delim;
    
    private List<PnPDeviceInfo> pnPDeviceInfoList = new ArrayList<PnPDeviceInfo>();
    
	public CSVtoPnPDeviceInfoList(String fileName, String delim) {
		this.fileToParse = fileName;
		this.delim = delim;
		
	}
	
	public List<PnPDeviceInfo> getPnPDeviceInfoListFromCSV() {
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(fileToParse));
			String nameStr = reader.readLine(); // Get the attribute names
			
			String valueStr = null;
			PnPDeviceInfo pnPDeviceInfo;
			String line = null; 
			while ((line = reader.readLine()) != null) { // Get the attribute values for each device
				valueStr = line;	
				pnPDeviceInfoList.add(new PnPDeviceInfo(nameStr,valueStr,delim));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}		
		return pnPDeviceInfoList;
	}
	
}
