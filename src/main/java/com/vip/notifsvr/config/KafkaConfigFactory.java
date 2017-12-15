package com.vip.notifsvr.config;

import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

import io.netty.util.internal.StringUtil;

public class KafkaConfigFactory implements NsConfigFactory {

	public NsConfiguration create(NsConfiguration config , NsConfig obj) {
		String brokers = System.getenv("VIP_NS_KAFKA_BROKERS");
		if (!StringUtil.isNullOrEmpty(brokers)) {
			config.setBrokers(brokers);
		} else {
			return null;
		}
		
		return config;
	}

}
