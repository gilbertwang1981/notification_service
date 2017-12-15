package com.vip.notifsvr.consts;

import io.netty.util.AttributeKey;

public interface NsChannelAttrKey {
	public static final AttributeKey<String> DT_CHANNEL_KEY = AttributeKey.valueOf("ns.devicetoken"); 
}
