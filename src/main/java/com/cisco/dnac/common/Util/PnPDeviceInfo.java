package com.cisco.dnac.common.Util;

import java.util.ArrayList;
import java.util.List;


public class PnPDeviceInfo {
	
	private List<PnPDeviceInfoAttr> deviceInfoAttrList = new ArrayList<PnPDeviceInfoAttr>();

	public PnPDeviceInfo(
			String nameStr,  // names of attributes
			String valueStr, // values of attributes
			String cvsSplitBy) // delimiter
	{
		
		System.out.println("nameStr : " + nameStr);
		System.out.println("valueStr: " + valueStr);
		
		String[] names = nameStr.split(cvsSplitBy);
		String[] values = valueStr.split(cvsSplitBy);
		
		if ((names.length == 0) || (values.length == 0)){
			System.err.println("Number of names or values are zero");
		} else if ( names.length != values.length) {
			System.err.println("Number of names and valiues don't match");
		} else {
			for (int i=0; i < names.length; i++ ) {
				deviceInfoAttrList.add(new PnPDeviceInfoAttr(names[i], values[i]));
			}		
		}
	}
	
	public List<PnPDeviceInfoAttr> getPnPDeviceInfoAttrList() {
		return deviceInfoAttrList;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for ( PnPDeviceInfoAttr deviceInfoAttr : deviceInfoAttrList) {
			sb.append(deviceInfoAttr.getName() + ": " +   deviceInfoAttr.getValue() + " ");
			
		}
		return sb.toString();
	}
	
}
