package org.bitwhite.sdk.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeader;

public final class Api {
	private String host;
	private HttpClient client;
	private String magic;
	
	public Api(String host, String magic) {
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("version", ""));
		headers.add(new BasicHeader("magic", magic));
		
		client = HttpClientBuilder.create()
	            .setRedirectStrategy(new LaxRedirectStrategy())
	            .setDefaultHeaders(headers).build();
		this.host = host;
	}
	
	public PostRequest post(String uri) {
		
		return new PostRequest(host + uri, getClient());
	}

	public GetRequest get(String uri) {
		return new GetRequest(host + uri, getClient());
	}
	
	public HttpClient getClient() {
		
		return client;
	}
	
}
