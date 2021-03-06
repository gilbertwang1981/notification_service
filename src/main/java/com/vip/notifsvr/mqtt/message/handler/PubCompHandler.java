package com.vip.notifsvr.mqtt.message.handler;

import java.io.IOException;

import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.Message.Header;
import com.vip.notifsvr.mqtt.proto.PubCompMessage;

public class PubCompHandler implements MessageHandler {

    public Message handleMessage(Header header) throws IOException {
    	NotifyDeviceMgr.getInstance().addAcksCtr();
    	
        return new PubCompMessage(header);
    }
}
