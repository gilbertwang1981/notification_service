package com.vip.notifsvr.mqtt.message.processer;

import java.io.IOException;

import com.vip.notifsvr.consts.NsChannelAttrKey;
import com.vip.notifsvr.device.NotifyDevice;
import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.mqtt.proto.ConnAckMessage;
import com.vip.notifsvr.mqtt.proto.ConnectMessage;
import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.rabbitmq.RabbitMqMgr;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.internal.StringUtil;

public class ConnectProcesser implements Processer {

	public boolean process(Message msg, ChannelHandlerContext ctx) throws IOException {	
		NotifyDevice device = new NotifyDevice();
		device.setChannel(ctx.channel());
		
		ConnectMessage connMsg = (ConnectMessage)msg;
		device.setDeviceToken(connMsg.getClientId());
		
		device.setCreateTime(System.currentTimeMillis());
		device.setLastUpdateTime(System.currentTimeMillis());
		
		device.setPnsId(RabbitMqMgr.getInstance().getNodeId());
		
		Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
		key.set(device.getDeviceToken());
		
		ConnAckMessage ackMessage = null;
		if (StringUtil.isNullOrEmpty(connMsg.getClientId())){
			ackMessage = new ConnAckMessage(ConnAckMessage.ConnectionStatus.NOT_AUTHORIZED);
		} else {
			ackMessage = new ConnAckMessage(ConnAckMessage.ConnectionStatus.ACCEPTED);
		}
		
		ctx.channel().writeAndFlush(ackMessage);
		
		return NotifyDeviceMgr.getInstance().registerDevice(device);
	}
}