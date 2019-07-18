package com.cisco.dnac.scheduler.exceptions;

public class RunLocalException extends Exception {
	String message = null;
	public RunLocalException(String message){
		super(message);
	}
}
