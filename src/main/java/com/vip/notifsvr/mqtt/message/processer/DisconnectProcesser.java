package com.vip.notifsvr.mqtt.message.processer;

import java.io.IOException;

import com.vip.notifsvr.mqtt.proto.Message;
import io.netty.channel.ChannelHandlerContext;

public class DisconnectProcesser implements Processer {
	public boolean process(Message msg, ChannelHandlerContext ctx) throws IOException {
		if (ctx.channel() != null) {
			ctx.channel().close();
		}
		
		return true;
	}

}
