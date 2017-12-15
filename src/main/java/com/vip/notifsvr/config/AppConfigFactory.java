package com.vip.notifsvr.config;

import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

public class AppConfigFactory implements NsConfigFactory{

	public NsConfiguration create(NsConfiguration config , NsConfig obj) {
		config.setBossThreads(Integer.parseInt(obj.getTcp_boss_threads()));
		config.setWorkerThreads(Integer.parseInt(obj.getTcp_worker_threads()));
		config.setServerPort(Integer.parseInt(obj.getTcp_port()));
		config.setTcpMaxIdle(Integer.parseInt(obj.getTcp_max_idle_ms()));
        
        return config;
	}
}
