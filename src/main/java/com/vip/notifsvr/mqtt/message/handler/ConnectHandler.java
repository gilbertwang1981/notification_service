package com.vip.notifsvr.mqtt.message.handler;

import java.io.IOException;

import com.vip.notifsvr.mqtt.proto.ConnectMessage;
import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.Message.Header;


public class ConnectHandler implements MessageHandler {
    public Message handleMessage(Header header) throws IOException {
        return new ConnectMessage(header);
    }

}
