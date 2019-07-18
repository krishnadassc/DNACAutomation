package com.cisco.dnac.scheduler.exceptions;

public class NodeNotReachableException extends Exception {
	String message = null;
	public NodeNotReachableException(String message){
		super(message);
	}
}
