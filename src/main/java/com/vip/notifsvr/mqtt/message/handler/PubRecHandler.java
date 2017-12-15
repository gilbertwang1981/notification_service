package com.vip.notifsvr.mqtt.message.handler;

import java.io.IOException;

import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.Message.Header;
import com.vip.notifsvr.mqtt.proto.PubRecMessage;

public class PubRecHandler implements MessageHandler {
    public Message handleMessage(Header header) throws IOException {
        return new PubRecMessage(header);
    }

}
