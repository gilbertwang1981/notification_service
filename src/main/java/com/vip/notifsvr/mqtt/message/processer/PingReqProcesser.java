package com.vip.notifsvr.mqtt.message.processer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.consts.NsChannelAttrKey;
import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.PingReqMessage;
import com.vip.notifsvr.mqtt.proto.PingRespMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;

public class PingReqProcesser implements Processer {
	private static Logger logger = LoggerFactory.getLogger(PingReqProcesser.class);
	
	public boolean process(Message msg, ChannelHandlerContext ctx) throws IOException {
		PingRespMessage presm = new PingRespMessage();

		PingReqMessage pingReqMessage = (PingReqMessage) msg;
        presm.setPingMessageId(pingReqMessage.getPingMessageId());
        
        ctx.writeAndFlush(presm);
        
        Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
        
        NotifyDeviceMgr.getInstance().updateLastUpdateTime(key.get());
        
        logger.debug("Got Ping Request From Client," + ctx.channel().toString());
        
		return true;
	}
}
