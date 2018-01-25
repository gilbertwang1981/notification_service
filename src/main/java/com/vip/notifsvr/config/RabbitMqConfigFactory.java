package com.vip.notifsvr.config;

import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

import io.netty.util.internal.StringUtil;

public class RabbitMqConfigFactory implements NsConfigFactory {

	public NsConfiguration create(NsConfiguration config , NsConfig obj) {
		String rabbitMqHost = System.getenv("VIPNS_RABBITMQ_HOST");
		if (StringUtil.isNullOrEmpty(rabbitMqHost)) {
			return null;
		} else {
			config.setMqHosts(rabbitMqHost);
		}
		
		String rabbitMqSPort = System.getenv("VIPNS_RABBITMQ_PORT");
		if (StringUtil.isNullOrEmpty(rabbitMqSPort)) {
			return null;
		} else {
			config.setMqPort(Integer.parseInt(rabbitMqSPort));
		}
		
		String rabbitMqUser = System.getenv("VIPNS_RABBITMQ_LOGIN");
		if (StringUtil.isNullOrEmpty(rabbitMqUser)) {
			return null;
		} else {
			config.setMqUser(rabbitMqUser);
		}
		
		String rabbitMqPass = System.getenv("VIPNS_RABBITMQ_PASSWORD");
		if (StringUtil.isNullOrEmpty(rabbitMqPass)) {
			return null;
		} else {
			config.setMqPass(rabbitMqPass);
		}
		
		String rabbitMqVHost = System.getenv("VIPNS_RABBITMQ_VHOST");
		if (StringUtil.isNullOrEmpty(rabbitMqVHost)) {
			return null;
		} else {
			config.setMqVHost(rabbitMqVHost);
		}
		
		String queueNamePrefix = System.getenv("VIPNS_RABBITMQ_QUEUE_NAME_PREFIX");
		if (StringUtil.isNullOrEmpty(queueNamePrefix)) {
			return null;
		} else {
			config.setQueueNamePrefix(queueNamePrefix);
		}
		
		config.setPnsId(Integer.parseInt(obj.getId()));
		
		String thrNum = System.getenv("VIPNS_RABBITMQ_CTHR_NUM");
		if (StringUtil.isNullOrEmpty(thrNum)) {
			return null;
		} else {
			config.setMqConsumerThreads(Integer.parseInt(thrNum));
		}
		
		return config;
	}
}
