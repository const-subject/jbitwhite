package org.bitwhite.sdk.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.bitwhite.sdk.interfaces.Serializable;

public class PostRequest extends DefaultRequest {
	private StringEntity entity;
	
	public PostRequest(String host) {
		super(host);
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
			response = Api.getClient().execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getStringResponse(response);	
	}
}
