package com.vip.notifsvr.config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.domain.NsCfgObject;
import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;
import com.vip.notifsvr.util.NsHttpUtil;
import com.vip.notifsvr.util.NsUtil;

public class NsConfigMgr {
	private static Logger logger = LoggerFactory.getLogger(NsConfigMgr.class);
	
	private static NsConfigMgr instance = null;
	
	private NsConfiguration config = new NsConfiguration();
	
	private ObjectMapper mapper = new ObjectMapper(); 
	
	public static NsConfigMgr getInstance(){
		if (instance == null) {
			instance = new NsConfigMgr();
		}
		
		return instance;
	}
	
	public boolean initialize() {
		try {
			String uri = System.getenv("VIPNS_CFG_URL");
			if (uri == null) {
				logger.error("the env [VIPNS_CFG_URL] should be set.");
				
				return false;
			}
			
			HttpGet gReq = new HttpGet(uri + NsUtil.getLocalHostLANAddress().getHostAddress());
			HttpResponse response = NsHttpUtil.getInstance().execute(gReq , NsConstDefinition.HTTP_CLIENT_TIMEOUT);
			if (response == null || response.getStatusLine().getStatusCode() != 200) {
				logger.error("get the configuration from the server failed.");
				
				return false;
			}
			
			HttpEntity httpEntity = response.getEntity();  
            String result = EntityUtils.toString(httpEntity);
            NsHttpUtil.getInstance().destory();
            
            mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            NsCfgObject cfg = mapper.readValue(result , NsCfgObject.class);
            NsConfig cf = mapper.readValue(cfg.getConfig() , NsConfig.class);
			
			NsConfigFactory appFactory = new AppConfigFactory();
			if (appFactory.create(config , cf) == null) {
				return false;
			}
			
			NsConfigFactory redisFactory = new RedisConfigFactory();
			if (redisFactory.create(config , cf) == null) {
				return false;
			}
			
			NsConfigFactory mqFactory = new RabbitMqConfigFactory();
			if (mqFactory.create(config , cf) == null) {
				return false;
			}
			
			NsConfigFactory kafkaFactory = new KafkaConfigFactory();
			if (kafkaFactory.create(config , cf) == null) {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			logger.error("exception caught", e);
			
			return false;
		} 
	}
	
	public NsConfiguration getConfig() {
		return config;
	}
}
