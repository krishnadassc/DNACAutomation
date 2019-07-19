package com.cisco.dnac.common.Util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ObsSSOAuthInterceptor implements ClientHttpRequestInterceptor {

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private final String username;

	private final String password;

	private RestTemplate restTemplate;
	

	public RestTemplate getRestTemplate() {
		if(restTemplate != null) {
			return this.restTemplate;
		}
	    TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
	        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
	            return true;
	        }
	    };
	    SSLContext sslContext;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			
			this.restTemplate = new RestTemplate(requestFactory);
			restTemplate.setMessageConverters(
					Arrays.asList(new FormHttpMessageConverter(), new StringHttpMessageConverter()));
			restTemplate.getInterceptors()
					.add(new DNACBasicAuthInterceptor(this.username,
							this.password));
			
			return restTemplate;
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	    
	}
	private String url;

	private String authToken;

	private Logger logger = Logger.getLogger(ObsSSOAuthInterceptor.class);

	public void getobssoCookies() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("userid", username);
			headers.add("password", password);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			
			RestTemplate restTemplate  = getRestTemplate();
			ResponseEntity<String> callback = restTemplate.postForEntity(url, entity, String.class);
			if (callback.getStatusCode().is2xxSuccessful()) {
				headers = new HttpHeaders();
				String token = callback.getBody();
				JsonParser parser= new JsonParser();
				JsonObject element = (JsonObject) parser.parse(token);
				authToken = element.get("Token").getAsString();
			} else {
				authToken = null;
			}
		} catch (Exception e) {
			authToken = null;
		}

	}


	public ObsSSOAuthInterceptor(String username, String password, String url) {
		Assert.hasLength(username, "Username must not be empty");
		this.username = username;
		this.password = (password != null ? password : "");
		this.url = url;
	}

	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		ClientHttpResponse response = null;
		logger.debug("request type " + request.getMethod().toString());
		logger.debug("request class " + request.getHeaders().getClass());
		for (int i = 0; i < 3; i++) {
			try {
				if (authToken == null) {
					getobssoCookies();
					if (authToken == null) {
						continue;
					}
				}
				logger.info("oAuthToken " + authToken);
				HttpRequest wrapper = new HttpRequestWrapper(request);
				wrapper.getHeaders().set("X-Auth-Token", authToken);
				logger.debug("wrapper url " + wrapper.getURI());
				response = execution.execute(wrapper, body);
				logger.info("response : " + response.getStatusCode());
				logger.info("response : " + response.getStatusText());
				if (response.getStatusCode().equals(HttpStatus.FOUND)) {
					authToken = null;
				} else {
					break;
				}
			} catch (Exception e) {
				logger.error("Error in ObsCookie header", e);
				authToken = null;
			}
		}
		if (response == null && authToken == null) {
			throw new IOException("invalid user credentials, not able to generate a valid obssocookie token");
		}
		return response;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
