package org.bitwhite.sdk.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.bitwhite.sdk.interfaces.Serializable;

public class PostRequest extends DefaultRequest {
	private StringEntity entity;
	private HttpClient client;
	
	public PostRequest(String host, HttpClient client) {
		super(host);
		this.client = client;
	}
	
	public PostRequest setObject(Serializable obj) {
		try {
			entity = new StringEntity(obj.serialize());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public String execute() {
		HttpUriRequest request = RequestBuilder
    			.post()
    			.setUri(getHost())
    			.addHeader("Content-Type", "application/json")
    			.setEntity(entity).build();
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getStringResponse(response);	
	}
}
