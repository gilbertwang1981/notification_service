package com.vip.notifsvr.mqtt.message.processer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.consts.NsChannelAttrKey;
import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.mqtt.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;

public class PingRespProcesser implements Processer {
	private static Logger logger = LoggerFactory.getLogger(PingRespProcesser.class);
	
	public boolean process(Message msg, ChannelHandlerContext ctx) throws IOException {
		Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
		
		NotifyDeviceMgr.getInstance().updateLastUpdateTime(key.get());
		
		logger.debug("Got Ping Response From Client," + ctx.channel().toString());
		
		return true;
	}
}
