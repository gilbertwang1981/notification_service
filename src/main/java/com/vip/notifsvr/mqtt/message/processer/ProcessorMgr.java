package com.vip.notifsvr.mqtt.message.processer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.vip.notifsvr.mqtt.proto.Message;

import io.netty.channel.ChannelHandlerContext;

public class ProcessorMgr {
	private static ProcessorMgr instance = null;
	private Map<Message.Type , Processer> processors = new HashMap<Message.Type , Processer>();
	
	public static ProcessorMgr getInstance(){
		if (instance == null) {
			instance = new ProcessorMgr();
		}
		
		return instance;
	}
	
	public void initailize(){
		processors.put(Message.Type.CONNACK , new ConnAckProcesser());
		processors.put(Message.Type.CONNECT , new ConnectProcesser());
		processors.put(Message.Type.DISCONNECT , new DisconnectProcesser());
		processors.put(Message.Type.PINGREQ , new PingReqProcesser());
		processors.put(Message.Type.PINGRESP , new PingRespProcesser());
		processors.put(Message.Type.PUBACK , new PubAckProcesser());
		processors.put(Message.Type.PUBCOMP , new PubCompProcesser());
		processors.put(Message.Type.PUBLISH , new PublishProcesser());
		processors.put(Message.Type.PUBREC , new PubRecProcesser());
		processors.put(Message.Type.PUBREL , new PubRelProcesser());
		processors.put(Message.Type.SUBACK , new SubAckProcesser());
		processors.put(Message.Type.SUBSCRIBE , new SubscribeProcesser());
		processors.put(Message.Type.UNSUBACK , new UnsubAckProcesser());
		processors.put(Message.Type.UNSUBSCRIBE , new UnsubscribeProcesser());
	}
	
	public boolean execute(Message msg , ChannelHandlerContext ctx) throws IOException {
		Processer processor = processors.get(msg.getType());
		if (processor == null) {
			return false;
		}
		
		return processor.process(msg , ctx);
	}
}
