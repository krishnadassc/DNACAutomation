package com.cisco.dnac.common.Util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("botRestClient")
public class BOTRestClientUtil implements RestClient{

	  @Value("${bot.api.username}")
	  private String username;

	  @Value("${bot.api.password}")
	  private String password;
	  
	  @Value("${bot.api.authurl}")
	  private String authUrl;

	  @Value("${bot.api.authtype}")
	  private String authType;
	  
	  @Value("${bot.api.host}")
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
			RequestEntity<String> reqEntity = new RequestEntity<String>(body, HttpMethod.POST, new URI("https://"+host+"/"+url));
			ResponseEntity<String> respEntity = getRestTemplate().exchange(reqEntity, String.class);
			return respEntity;			
		}catch (Exception e) {
			return null;
		}
	}

	
}
