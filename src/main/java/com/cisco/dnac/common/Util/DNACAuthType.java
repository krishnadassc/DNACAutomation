package com.cisco.dnac.common.Util;

import org.apache.commons.lang3.StringUtils;

public enum DNACAuthType {

	NO_AUTH("no_auth"),
	BASIC("basic"),
	CUSTOM_HEADER("header"),
	DIGEST("digest"),
	OAUTH2("oauth2"),
	AUTH_TOKEN("authToken");

	private String value;

	private DNACAuthType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static DNACAuthType getAuthType(String authType) {
		if (StringUtils.isBlank(authType)) {
			return NO_AUTH;
		}
		for (DNACAuthType auth : values()) {
			if (StringUtils.equals(auth.getValue(), authType)) {
				return auth;
			}
		}
		return null;
	}
}
