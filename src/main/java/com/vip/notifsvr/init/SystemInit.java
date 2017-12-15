package com.vip.notifsvr.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.config.NsConfigMgr;
import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.jobs.NsStatJob;
import com.vip.notifsvr.kafka.KafkaMgr;
import com.vip.notifsvr.mqtt.message.processer.ProcessorMgr;
import com.vip.notifsvr.redis.RedisMgr;
import com.vip.notifsvr.util.NsHttpUtil;
import com.vip.notifsvr.zookeeper.NsClusterZkAgent;
import com.vip.notifsvr.notifier.Publisher;
import com.vip.notifsvr.rabbitmq.RabbitMqMgr;

public class SystemInit {
	private static Bootstrap serverBootstrap = new Bootstrap();
	
	private static Logger logger = LoggerFactory.getLogger(SystemInit.class);
	
	public static void main(String [] args) {
		NotifyDeviceMgr.getInstance();
		Publisher.getInstance();
		
		NsHttpUtil.getInstance().initialize();
		
		if (!NsConfigMgr.getInstance().initialize()){
			logger.error("initialize configuration failed.");
			
			return;
		}
		
		logger.info("loading configuration:" + NsConfigMgr.getInstance().getConfig().toString());
		
		if (!KafkaMgr.getInstance().initialize()) {
			logger.error("initialize kafka failed.");
			
			return;
		}
		
		if (!RedisMgr.getInstance().initialize()) {
			logger.error("initialize redis cluster failed.");
			
			return;
		}
		
		NsStatJob.getInstance().start();
		
		ProcessorMgr.getInstance().initailize();
		if (!RabbitMqMgr.getInstance().initialize()){
			logger.error("initialize rabbitmq failed.");
			
			return;
		}
		
		if (!NsClusterZkAgent.getInstance().connectZk()){
			logger.error("connect to ZK failed.");
			
			return;
		}
		
		if (!NsClusterZkAgent.getInstance().register()) {
			logger.error("register to ZK failed.");
			
			return;
		}

		if (!serverBootstrap.initialize(NsConfigMgr.getInstance().getConfig().getServerPort())) {
			logger.error("start server @ " + NsConfigMgr.getInstance().getConfig().getServerPort() + " failed.");
		}
	}
}
