package com.cisco.dnac.common.Util;

import java.util.List;

public class CSVParserMain {

	private static String DEFAULT_CSV_FILE = "src/main/resources/sample.csv";
	
	public static void main(String[] args) throws Exception {
		System.out.println("This is a test");
		System.out.println("Working Directory " + System.getProperty("user.dir"));
		
		CSVtoPnPDeviceInfoList parseCSVtoPnPDeviceInfoList = new 	CSVtoPnPDeviceInfoList(
				DEFAULT_CSV_FILE, CSVtoPnPDeviceInfoList.DEFAULT_DELIM);
		
		List<PnPDeviceInfo> deviceInfoList   = parseCSVtoPnPDeviceInfoList.getPnPDeviceInfoListFromCSV();
		
		for  (PnPDeviceInfo deviceInfo: deviceInfoList) {
			System.out.println(deviceInfo.toString());
		}		
	}
}
