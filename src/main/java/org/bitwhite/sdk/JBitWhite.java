package org.bitwhite.sdk;

import org.bitwhite.sdk.crypto.Security;
import org.bitwhite.sdk.network.Api;

import com.google.gson.Gson;

public final class JBitWhite {
	private Security security;
	private static Gson gson = new Gson();
	private Api api;
	
	
	public JBitWhite(String api) {
		this(api, BTWConst.MAGIC, new Security());
	}
	
	public JBitWhite(String api, String magic) {
		this(api, magic, new Security());
	}
	
	public JBitWhite(String api, String magic, Security security) {
		this.security = security;
		this.api = new Api(api, magic);
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
