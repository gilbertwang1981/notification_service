package com.vip.notifsvr.config;

import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

import io.netty.util.internal.StringUtil;

public class ZkConfigFactory implements NsConfigFactory {

	public NsConfiguration create(NsConfiguration config , NsConfig obj) {
		String zkHost = System.getenv("VIP_NS_ZK_HOST");
		if (!StringUtil.isNullOrEmpty(zkHost)) {
			config.setZkHost(zkHost);
		} else {
			return null;
		}
		
		String zkTimeout = System.getenv("VIP_NS_ZK_CLIENT_TMO");
		if (!StringUtil.isNullOrEmpty(zkTimeout)) {
			config.setZkClientTmo(Integer.parseInt(zkTimeout));
		} else {
			config.setZkClientTmo(Integer.parseInt(NsConstDefinition.DEFAULT_ZK_CLIENT_TIMEOUT));
		}
		
		return config;
	}

}
