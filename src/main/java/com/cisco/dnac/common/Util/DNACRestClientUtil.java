package com.cisco.dnac.common.Util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service("dnacRestClient")
public class DNACRestClientUtil implements RestClient{

	  Logger logger = Logger.getLogger(DNACRestClientUtil.class);
	  @Value("${dnac.api.username}")
	  private String username;

	  @Value("${dnac.api.password}")
	  private String password;
	  
	  @Value("${dnac.api.authurl}")
	  private String authUrl;

	  @Value("${dnac.api.authtype}")
	  private String authType;
	  
	  @Value("${dnac.api.host}")
	  private String host;
	  
	@Autowired
	private RestClientUtil restClientUtil;

	public RestTemplate getRestTemplate() {
		Map<String, String> authAttribute = new HashMap<String, String>();
		authAttribute.put(DNACAuthSettingsKey.USERNAME.getValue(),username);
		authAttribute.put(DNACAuthSettingsKey.PASSWORD.getValue(), password);
		authAttribute.put(DNACAuthSettingsKey.AUTH_URL.getValue(),authUrl);
		authAttribute.put(DNACAuthSettingsKey.AUTH_TYPE.getValue(),authType);
		return restClientUtil.getRestTemplate(host, authAttribute);
	}
	
	public ResponseEntity<String> exchange(String body, HttpMethod method, String url) {
		try {
			String urlVal = "https://"+host+"/"+url;
			logger.info(urlVal	);
			RequestEntity<String> reqEntity = new RequestEntity<String>(body, method, new URI(urlVal));
			ResponseEntity<String> respEntity = getRestTemplate().exchange(reqEntity, String.class);
			return respEntity;			
		}catch (Exception e) {
			return null;
		}
	}

}
