package com.vip.notifsvr.mqtt.message.handler;

import java.io.IOException;

import com.vip.notifsvr.mqtt.proto.ConnAckMessage;
import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.Message.Header;

public class ConnAckHandler implements MessageHandler {

    public Message handleMessage(Header header) throws IOException {
        return new ConnAckMessage(header);
    }

}
