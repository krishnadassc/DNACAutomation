package com.cisco.dnac.common.Util;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface RestClient {
	public RestTemplate getRestTemplate();
	public ResponseEntity<String> exchange(String body, HttpMethod method, String url);
		
}
