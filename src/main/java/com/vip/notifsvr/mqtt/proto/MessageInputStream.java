package com.vip.notifsvr.mqtt.proto;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.mqtt.proto.Message.Type;
import com.vip.notifsvr.mqtt.message.handler.ConnAckHandler;
import com.vip.notifsvr.mqtt.message.handler.ConnectHandler;
import com.vip.notifsvr.mqtt.message.handler.DisconnectHandler;
import com.vip.notifsvr.mqtt.message.handler.MessageHandler;
import com.vip.notifsvr.mqtt.message.handler.PingReqHandler;
import com.vip.notifsvr.mqtt.message.handler.PingRespHandler;
import com.vip.notifsvr.mqtt.message.handler.PubAckHandler;
import com.vip.notifsvr.mqtt.message.handler.PubCompHandler;
import com.vip.notifsvr.mqtt.message.handler.PubRecHandler;
import com.vip.notifsvr.mqtt.message.handler.PubRelHandler;
import com.vip.notifsvr.mqtt.message.handler.PublishHandler;
import com.vip.notifsvr.mqtt.message.handler.SubAckHandler;
import com.vip.notifsvr.mqtt.message.handler.SubscribeHandler;
import com.vip.notifsvr.mqtt.message.handler.UnsubAckHandler;
import com.vip.notifsvr.mqtt.message.handler.UnsubscribeHandler;
import com.vip.notifsvr.mqtt.proto.Message.Header;

public class MessageInputStream implements Closeable {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageInputStream.class);

    private static Map<Message.Type, MessageHandler> processers;

    static{
        Map<Message.Type, MessageHandler> map = new HashMap<Message.Type, MessageHandler>();
        map.put(Type.CONNACK, new ConnAckHandler());
        map.put(Type.PUBLISH, new PublishHandler());
        map.put(Type.PUBACK, new PubAckHandler());
        map.put(Type.PUBREC, new  PubRecHandler());
        map.put(Type.PUBREL, new PubRelHandler());
        map.put(Type.PUBCOMP, new PubCompHandler());
        map.put(Type.SUBACK, new SubAckHandler());
        map.put(Type.UNSUBACK, new UnsubAckHandler());
        map.put(Type.PINGRESP, new PingRespHandler());
        map.put(Type.CONNECT, new ConnectHandler());
        map.put(Type.SUBSCRIBE, new SubscribeHandler());
        map.put(Type.UNSUBSCRIBE, new UnsubscribeHandler());
        map.put(Type.PINGREQ, new PingReqHandler());
        map.put(Type.DISCONNECT, new DisconnectHandler());
        processers = Collections.unmodifiableMap(map);
    }
	private InputStream in;

	public MessageInputStream(InputStream in) {
		this.in = in;
	}

	public Message readMessage() throws IOException {
		byte flags = (byte) in.read();
		
		Header header = new Header(flags);
        if (logger.isDebugEnabled()) {
            logger.debug("header:{}", header);
        }
		if (!processers.containsKey(header.getType())) {
		    throw new UnsupportedOperationException(
                    "No support for deserializing " + header.getType()
                            + " messages");
		}
		MessageHandler p = processers.get(header.getType());
        Message msg = p.handleMessage(header);
 
		msg.read(in);
		return msg;
	}

    public void close() throws IOException {
		in.close();
	}
}
