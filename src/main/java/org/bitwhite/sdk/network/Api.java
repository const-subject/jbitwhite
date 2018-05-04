package org.bitwhite.sdk.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeader;
import org.bitwhite.sdk.BTWConst;

public final class Api {
	private String host;
	private static HttpClient client;
	
	static {
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("version", ""));
		headers.add(new BasicHeader("magic", BTWConst.MAGIC));
		
		client = HttpClientBuilder.create()
	            .setRedirectStrategy(new LaxRedirectStrategy())
	            .setDefaultHeaders(headers).build();
	}
	
	public Api(String host) {
		
		this.host = host;
	}
	
	public PostRequest post(String uri) {
		
		return new PostRequest(host + uri);
	}

	public GetRequest get(String uri) {
		return new GetRequest(host + uri);
	}
	
	public static HttpClient getClient() {
		
		return client;
	}
	
}
