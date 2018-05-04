package org.bitwhite.sdk;

import org.bitwhite.sdk.crypto.Security;
import org.bitwhite.sdk.network.Api;

import com.google.gson.Gson;

public final class JBitWhite {
	private Security security;
	private static Gson gson = new Gson();
	private Api api;
	
	
	public JBitWhite(Api api) {
		this(api, new Security());
	}
	
	public JBitWhite(Api api, Security security) {
		this.security = security;
		this.api = api;
	}
	
	public Security getSecurity() {
		return security;
	}
	
	public static Gson getGson() {
		return gson;
	}
	
	public Api getApi() {
		return api;
	}
	
}
