package org.bitwhite.sdk.network;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.RequestBuilder;

public class GetRequest extends DefaultRequest {
	private RequestBuilder builder;
	private HttpClient client;
	
	public GetRequest(String host, HttpClient client) {
		super(host);
		builder = RequestBuilder.get().setUri(getHost());
		this.client = client;
	}

	@Override
	public String execute() {
		HttpResponse response = null;
		try {
			response = client.execute(builder.build());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getStringResponse(response);
	}
	
	public GetRequest addParam(String k, String v) {
		builder.addParameter(k, v);
		return this;
	}

}
