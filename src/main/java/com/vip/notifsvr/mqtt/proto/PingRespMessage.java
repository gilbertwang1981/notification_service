package com.vip.notifsvr.mqtt.proto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.vip.notifsvr.util.NsUtil;

import io.netty.util.internal.StringUtil;

public class PingRespMessage extends Message {

	private String pingMessageId;
	
	public PingRespMessage(Header header) throws IOException {
		super(header);
	}
	
	public PingRespMessage() {
		super(Type.PINGRESP);
	}
	
	@Override
	protected int messageLength() {
		int length = NsUtil.toMQttString(pingMessageId).length;
		return length;
	}
	
	@Override
	protected void readMessage(InputStream in, int msgLength)
			throws IOException {
		if(msgLength <= 0){
			return;
		}
		DataInputStream dis = new DataInputStream(in);
		pingMessageId = dis.readUTF();
	}

	@Override
	protected void writeMessage(OutputStream out) throws IOException {
		if(StringUtil.isNullOrEmpty(pingMessageId)){
			return;
		}
		DataOutputStream dos = new DataOutputStream(out);
		dos.writeUTF(pingMessageId);
		dos.flush();
	}

	public String getPingMessageId() {
		return pingMessageId;
	}

	public void setPingMessageId(String pingMessageId) {
		this.pingMessageId = pingMessageId;
	}
	
}
