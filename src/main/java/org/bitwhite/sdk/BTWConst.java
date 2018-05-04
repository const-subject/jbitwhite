package org.bitwhite.sdk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class BTWConst {

	public static final int CLIENT_DRIFT_SECONDS = 5;
	public static final int COIN = 100000000;
	public static final Date BEGIN_EPOCH;
	public static final String MAGIC = "5f5b3cf5";
	public static final String DEFAULT_HOST = "http://51.254.246.147:8888/";
	
	static {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		try {
			date = df.parse("2016-06-27T20:00:00Z");
		} catch (Exception ex) {}
		BEGIN_EPOCH = date;
	}

	public static class Fees {
		public static final long TRANSFER = 10000000;
		public static final long UIA_TRANSFER = 10000000;
		public static final long VOTE = 10000000;
		public static final long SECOND_SIGNATURE = 500000000;
		public static final long MULTI_SIGNATURE = 500000000;
		public static final long DELEGATE = 10000000000L;
		public static final long DAPP = 10000000000L;
	}

}