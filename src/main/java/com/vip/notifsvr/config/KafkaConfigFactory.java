package com.vip.notifsvr.config;

import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

import io.netty.util.internal.StringUtil;

public class KafkaConfigFactory implements NsConfigFactory {

	public NsConfiguration create(NsConfiguration config , NsConfig obj) {
		String brokers = System.getenv("VIPNS_KAFKA_BROKERS");
		if (!StringUtil.isNullOrEmpty(brokers)) {
			config.setBrokers(brokers);
		} else {
			return null;
		}
		
		String reportTopic = System.getenv("VIPNS_KAFKA_REPORT_TOPIC");
		if (!StringUtil.isNullOrEmpty(reportTopic)) {
			config.setReportTopic(reportTopic);
		} else {
			config.setReportTopic(NsConstDefinition.REPORT_KAFKA_TOPIC_NAME);
		}
		
		String loginTopic = System.getenv("VIPNS_KAFKA_LOGIN_TOPIC");
		if (!StringUtil.isNullOrEmpty(loginTopic)) {
			config.setLoginTopic(loginTopic);
		} else {
			config.setLoginTopic(NsConstDefinition.LOGIN_KAFKA_TOPIC_NAME);
		}
		
		String logoutTopic = System.getenv("VIPNS_KAFKA_LOGOUT_TOPIC");
		if (!StringUtil.isNullOrEmpty(logoutTopic)) {
			config.setLogoutTopic(logoutTopic);
		} else {
			config.setLogoutTopic(NsConstDefinition.LOGOUT_KAFKA_TOPIC_NAME);
		}
		
		return config;
	}

}
