package com.vip.notifsvr.config;

import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

public class RabbitMqConfigFactory implements NsConfigFactory {

	public NsConfiguration create(NsConfiguration config , NsConfig obj) {
		config.setMqHosts(obj.getMq_host());
		config.setMqPort(Integer.parseInt(obj.getMq_port()));
		config.setMqUser(obj.getMq_user());
		config.setMqPass(obj.getMq_pass());
		config.setMqVHost(obj.getMq_vhost());
		config.setQueueNamePrefix(obj.getMq_queue_prefix());
		config.setPnsId(Integer.parseInt(obj.getId()));
		config.setMqConsumerThreads(Integer.parseInt(obj.getMq_cthr_num()));
		
		return config;
	}
}
