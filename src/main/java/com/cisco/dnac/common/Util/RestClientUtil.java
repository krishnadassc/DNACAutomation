package com.cisco.dnac.common.Util;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestClientUtil {



	ReentrantLock lock = new ReentrantLock();

	private static ConcurrentHashMap<String, RestTemplate> restTemplateMap = new ConcurrentHashMap();

	/**
	 * @return the restTemplateMap
	 */
	public static ConcurrentHashMap<String, RestTemplate> getRestTemplateMap() {
		return restTemplateMap;
	}

	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	@Autowired
	protected WebApplicationContext context;

	private <T> T getEntityFromResource(Resource resource, Class<T> obj)
			throws InstantiationException, IllegalAccessException, IOException {
		File file = resource.getFile();
		ObjectMapper mapper = new ObjectMapper();
		T resultObj = mapper.readValue(file, obj);
		return resultObj;
	}
	

	public RestTemplate getRestTemplate(String hostIp, Map<String, String> authAttribute) {
		if (authAttribute == null) {
			return null;
		}
		String restTemplateKey = hostIp ;
		RestTemplate restTemplate = null;
		if (restTemplateMap.containsKey(restTemplateKey)) {
			return restTemplateMap.get(restTemplateKey);
		} else {
			restTemplate = getRestTemplateWithAuth(authAttribute);
			restTemplateMap.put(restTemplateKey, restTemplate);
		}
		return restTemplate;
	}

	private RestTemplate getRestTemplateWithAuth(Map<String, String> authAttribute) {
		DNACAuthType authType = authAttribute != null
				? DNACAuthType.getAuthType(authAttribute.get(DNACAuthSettingsKey.AUTH_TYPE.getValue()))
				: DNACAuthType.NO_AUTH;
		RestTemplate restTemplate = null;
		switch (authType) {
		case BASIC:
			restTemplate = basicAuthtemplateInterCeptor(authAttribute);
			break;
		case AUTH_TOKEN:
			restTemplate = obsCookieAuthtemplateInterceptor(authAttribute);
			break;
		default:
			restTemplate = new RestTemplate();
			restTemplate.setMessageConverters(
					Arrays.asList(new FormHttpMessageConverter(), new StringHttpMessageConverter()));
		}
		return restTemplate;
	}

	private RestTemplate obsCookieAuthtemplateInterceptor(Map<String, String> authAttribute) {
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
		
		RestTemplate obsSSORestTemplate = new RestTemplate(requestFactory);
		obsSSORestTemplate.setMessageConverters(
				Arrays.asList(new FormHttpMessageConverter(), new StringHttpMessageConverter()));
		obsSSORestTemplate.getInterceptors()
				.add(new ObsSSOAuthInterceptor(
						authAttribute.get(DNACAuthSettingsKey.USERNAME.getValue()),
						authAttribute.get(DNACAuthSettingsKey.PASSWORD.getValue()),
						authAttribute.get(DNACAuthSettingsKey.AUTH_URL.getValue())));

		return obsSSORestTemplate;
		}catch (Exception e) {
			return null;
		}
	}



	private RestTemplate basicAuthtemplateInterCeptor(Map<String, String> authAttribute) {
		final RestTemplate basicRestTemplate = new RestTemplate();
		basicRestTemplate.setMessageConverters(
				Arrays.asList(new FormHttpMessageConverter(), new StringHttpMessageConverter()));
		basicRestTemplate.getInterceptors()
				.add(new DNACBasicAuthInterceptor(
						authAttribute.get(DNACAuthSettingsKey.USERNAME.getValue()),
						authAttribute.get(DNACAuthSettingsKey.PASSWORD.getValue())));
		return basicRestTemplate;
	}







	

}
