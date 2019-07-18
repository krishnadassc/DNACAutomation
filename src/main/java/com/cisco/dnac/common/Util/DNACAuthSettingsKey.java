package com.cisco.dnac.common.Util;

public enum DNACAuthSettingsKey  {

	SETTINGS("settings"),
	HOSTNAME("host"),
	URL("url"),
	AUTH_TYPE("authType"),
	USERNAME("username"),
	PASSWORD("password"),
	AUTH_URL("authUrl"),
	OAUTH_CLIENT_TOKEN_URI("tokenUri"),
	PATH_EXPR("pathExpr"),
	TARGET_PATH("targetPath"),
	// Message related
	MSG_TIMEOUT_IN_HOURS("msgTimeoutInHours");

	private String value;

	private DNACAuthSettingsKey(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getAttribute() {
		return this.value;
	}

	public void setAttribute(String value) {
		this.value = value;
	}
}
