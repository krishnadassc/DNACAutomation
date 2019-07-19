package com.cisco.dnac.common.Util;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public interface RestClient {
	public RestTemplate getRestTemplate();
	public ResponseEntity<String> exchange(String body, HttpMethod method, String url);
	ResponseEntity<String> exchange(String body, HttpMethod method, String url, MultiValueMap<String, String> headerMap);
		
}
