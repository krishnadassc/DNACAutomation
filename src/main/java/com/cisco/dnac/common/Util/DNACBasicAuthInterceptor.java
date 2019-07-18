package com.cisco.dnac.common.Util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;


public class DNACBasicAuthInterceptor implements ClientHttpRequestInterceptor {

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private final String username;

	private final String password;

	private Logger logger = Logger.getLogger(DNACBasicAuthInterceptor.class);

	/**
	 * Create a new interceptor which adds a BASIC authorization header for the
	 * given username and password.
	 * 
	 * @param username
	 *            the username to use
	 * @param password
	 *            the password to use
	 */
	public DNACBasicAuthInterceptor(String username, String password) {
		Assert.hasLength(username, "Username must not be empty");
		this.username = username;
		this.password = (password != null ? password : "");
	}

	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {

		String token = Base64Utils.encodeToString((this.username + ":" + this.password).getBytes(UTF_8));
		HttpRequest wrapper = new HttpRequestWrapper(request);
		wrapper.getHeaders().set(HttpHeaders.AUTHORIZATION, "Basic " + token);
		logger.debug("wrapper url " + wrapper.getURI());
		logger.info("auth:" + wrapper.getHeaders().get("Authorization"));
		ClientHttpResponse response = execution.execute(wrapper, body);
		logger.debug("response : " + response.getStatusCode());
		return response;
	}

}
