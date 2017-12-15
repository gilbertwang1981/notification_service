package com.vip.notifsvr.config;

import com.vip.notifsvr.domain.NsConfig;
import com.vip.notifsvr.domain.NsConfiguration;

public interface NsConfigFactory {
	public NsConfiguration create(NsConfiguration config , NsConfig cfg);
}
