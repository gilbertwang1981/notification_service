package com.vip.notifsvr.codec;
import com.vip.notifsvr.mqtt.proto.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NsMessageEncoder extends MessageToByteEncoder<Message>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		if (!(msg instanceof Message)) {
			return ;
		}
		
		byte[] data = msg.toBytes();
	    out.writeBytes(data); 
	}
}
