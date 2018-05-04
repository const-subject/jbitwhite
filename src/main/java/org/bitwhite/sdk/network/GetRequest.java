package org.bitwhite.sdk.network;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.RequestBuilder;

public class GetRequest extends DefaultRequest {
	private RequestBuilder builder;
	public GetRequest(String host) {
		super(host);
		builder = RequestBuilder.get().setUri(getHost());
		
	}

	@Override
	public String execute() {
		HttpResponse response = null;
		try {
			response = Api.getClient().execute(builder.build());
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
