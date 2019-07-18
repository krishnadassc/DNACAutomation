package com.cisco.dnac.common.Util;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OAuthInterceptor implements ClientHttpRequestInterceptor {

	RestTemplate restTemplate = new RestTemplate();

	private String authUrl;

	private String oAuthToken;

	private long expiresIn = 0L;

	private Logger logger = Logger.getLogger(OAuthInterceptor.class);

	public void getOAuthToken() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			ResponseEntity<String> callback = restTemplate.postForEntity(authUrl, entity, String.class);
			if (callback.getStatusCode().is2xxSuccessful()) {
				headers = new HttpHeaders();
				Map<String, String> respMap = new ObjectMapper().readValue(callback.getBody(), Map.class);
				if (respMap != null && respMap.get("access_token") != null) {
					Integer tokenExpSec = (Integer) (respMap.get("expires_in") != null
							? respMap.get("expires_in") : 0);
					expiresIn = System.currentTimeMillis() + (Long.valueOf(tokenExpSec) * 1000);
					oAuthToken = respMap.get("access_token");
				}
			} else {
				oAuthToken = null;
			}
		} catch (Exception e) {
			oAuthToken = null;
		}

	}

	/**
	 * 
	 * @param clientId
	 *            the client Id to use
	 * @param clientSecret
	 *            the client secret to use
	 * @param url
	 *            the token url
	 */
	public OAuthInterceptor(String clientId, String clientSecret, String url) {
		Assert.hasLength(clientId, "Username must not be empty");
		this.authUrl = url + "?grant_type=client_credentials&client_id=" + clientId + "&client_secret="
				+ clientSecret;
	}


	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		ClientHttpResponse response = null;
		logger.debug("request type " + request.getMethod().toString());
		logger.debug("request class " + request.getHeaders().getClass());
		for (int i = 0; i < 3; i++) {
			try {
				if (oAuthToken == null || expiresIn < System.currentTimeMillis()) {
					getOAuthToken();
					if (oAuthToken == null) {
						continue;
					}
				}
				logger.info("oAuthToken " + oAuthToken);
				HttpRequest wrapper = new HttpRequestWrapper(request);
				wrapper.getHeaders().set("X-Auth-Token", oAuthToken);
				logger.debug("wrapper url " + wrapper.getURI());
				response = execution.execute(wrapper, body);
				logger.info("response : " + response.getStatusCode());
				if (response.getStatusCode().equals(HttpStatus.FOUND)) {
					oAuthToken = null;
				} else {
					break;
				}
			} catch (Exception e) {
				logger.error("Error in Oauth header", e);
				oAuthToken = null;
			}
		}
		return response;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return authUrl;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.authUrl = url;
	}

}
