package com.vip.notifsvr.mqtt.message.processer;

import java.io.IOException;

import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.PubRecMessage;
import com.vip.notifsvr.mqtt.proto.PubRelMessage;

import io.netty.channel.ChannelHandlerContext;

public class PubRelProcesser implements Processer {
	public boolean process(Message msg, ChannelHandlerContext ctx) throws IOException {
		PubRecMessage prm = (PubRecMessage) msg;
        ctx.channel().write(new PubRelMessage(prm.getMessageId()));
        
		return true;
	}

}
