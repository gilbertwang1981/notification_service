package com.vip.notifsvr.config;

import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

import io.netty.util.internal.StringUtil;

public class RedisConfigFactory implements NsConfigFactory{

	public NsConfiguration create(NsConfiguration config , NsConfig obj) {
		String maxIdle = System.getenv("VIP_NS_REDIS_MAX_IDLE");
		if (!StringUtil.isNullOrEmpty(maxIdle)) {
			config.setRedisMaxIdle(Integer.parseInt(maxIdle));
		} else {
			config.setRedisMaxIdle(NsConstDefinition.REDIS_MAX_IDLE);
		}
		
		String maxTotal = System.getenv("VIP_NS_REDIS_MAX_TOTAL");
		if (!StringUtil.isNullOrEmpty(maxTotal)) {
			config.setRedisMaxTotal(Integer.parseInt(maxTotal));
		} else {
			config.setRedisMaxTotal(NsConstDefinition.REDIS_MAX_TOTAL);
		}
		
		String maxWait = System.getenv("VIP_NS_REDIS_MAX_WAIT");
		if (!StringUtil.isNullOrEmpty(maxWait)) {
			config.setRedisMaxWait(Integer.parseInt(maxWait));
		} else {
			config.setRedisMaxWait(NsConstDefinition.REDIS_MAX_WAIT);
		}
		
		String minEvicatableIdleTime = System.getenv("VIP_NS_REDIS_MIN_EVICATABLE_IDLE_TIME");
		if (!StringUtil.isNullOrEmpty(minEvicatableIdleTime)) {
			config.setRedisMinEvicaIdleTime(Integer.parseInt(minEvicatableIdleTime));
		} else {
			config.setRedisMinEvicaIdleTime(NsConstDefinition.REDIS_MIN_EVI_TIME);
		}
		
		String minIdle = System.getenv("VIP_NS_REDIS_MIN_IDLE");
		if (!StringUtil.isNullOrEmpty(minIdle)) {
			config.setRedisMinIdle(Integer.parseInt(minIdle));
		} else {
			config.setRedisMinIdle(NsConstDefinition.REDIS_MIN_IDLE);
		}
		
		String timeBetweenEvicationRun = System.getenv("VIP_NS_REDIS_TIME_BETWEEN_EVICATION_RUN");
		if (!StringUtil.isNullOrEmpty(timeBetweenEvicationRun)) {
			config.setRedisTimeBetweenEvicaRun(Integer.parseInt(timeBetweenEvicationRun));
		} else {
			config.setRedisTimeBetweenEvicaRun(NsConstDefinition.REDIS_TIME_BETWEEN_EVICATION_RUN);
		}
		
		String address = System.getenv("VIP_NS_REDIS_HOSTS");
		if (!StringUtil.isNullOrEmpty(address)) {
			config.setRedisAddress(address);
		} else {
			return null;
		}
		
		return config;
	}
}
