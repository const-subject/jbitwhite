package org.bitwhite.sdk.network;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

public abstract class DefaultRequest {
	private String host;
	
	protected DefaultRequest(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}
	
	protected String getStringResponse(HttpResponse response) {
		try {
			return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract String execute();
}
