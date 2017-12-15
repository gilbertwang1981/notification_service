package com.vip.notifsvr.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.mqtt.proto.PublishMessage;

import io.netty.channel.Channel;

public class Publisher {
	private static Logger logger = LoggerFactory.getLogger(Publisher.class);
	
	private static Publisher instance = null;
	
	public static Publisher getInstance() {
		if (instance == null) {
			instance = new Publisher();
		}
		
		return instance;
	}
	
	public boolean publish(String deviceToken , String content , String topic) {
		Channel channel = NotifyDeviceMgr.getInstance().getChannel(deviceToken);
		if (channel == null) {
			return false;
		}
		
		try {
			channel.writeAndFlush(new PublishMessage(topic , content.getBytes("UTF-8")));
			
			NotifyDeviceMgr.getInstance().increase(true);
			
			return true;
		} catch (Exception e) {
			logger.error("send message failed." + channel.toString() + "/" + deviceToken);
			
			NotifyDeviceMgr.getInstance().increase(false);
			
			return false;
		}
	}
}
