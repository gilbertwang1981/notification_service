package com.vip.notifsvr.util;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NsHttpUtil {
	private static Logger logger = LoggerFactory.getLogger(NsHttpUtil.class);
	
	private static NsHttpUtil instance = null;
	
	private CloseableHttpClient httpclient = null;
	
	public static NsHttpUtil getInstance() {
		if (instance == null) {
			instance = new NsHttpUtil();
		}
		
		return instance;
	}

	public void initialize() {
		final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(sfr);
		cm.setDefaultMaxPerRoute(1000);
		cm.setMaxTotal(1000);
		httpclient = HttpClientBuilder.create().setConnectionManager(cm).build();
	}

	public HttpResponse execute(HttpGet request, Integer timeout) {
		HttpResponse httpResponse;
		try {
			if (timeout != null && timeout > 0) {
				final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
						.setConnectTimeout(timeout).build();
				request.setConfig(requestConfig);
			}
			httpResponse = httpclient.execute(request);
		} catch (Exception e) {
			logger.error("ClientProtocolException in request", e.getMessage());
			
			return null;
		}
		
		return httpResponse;
	}

	public void finalize() {
		try {
			httpclient.close();
		} catch (IOException e) {
			logger.error("syncHttpClient close error", e.getMessage());
		}
	}

}
