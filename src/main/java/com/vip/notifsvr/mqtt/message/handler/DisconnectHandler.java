package com.vip.notifsvr.mqtt.message.handler;

import java.io.IOException;

import com.vip.notifsvr.mqtt.proto.DisconnectMessage;
import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.Message.Header;

public class DisconnectHandler implements MessageHandler {

    public Message handleMessage(Header header) throws IOException {
        return new DisconnectMessage(header);
    }

}
