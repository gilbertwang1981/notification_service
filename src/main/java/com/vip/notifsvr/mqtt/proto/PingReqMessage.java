package com.vip.notifsvr.mqtt.proto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.vip.notifsvr.util.NsUtil;

public class PingReqMessage extends Message {

	private static final int PING_HEADER_SIZE = 1;// hasJsonMessages和hasAppVersion需要占1字节
	
	private boolean hasJsonMessages;
	private boolean hasAppVersion;
	private String pingMessageId = "0";
	private String appVersion;
	private String jsonMessages;
	
	public PingReqMessage() {
		super(Type.PINGREQ);
	}
	
	public PingReqMessage(Header header) throws IOException{
		super(header);
	}
	
	@Override
	protected int messageLength() {
		int length = NsUtil.toMQttString(jsonMessages).length;
		length += NsUtil.toMQttString(pingMessageId).length;
		length += NsUtil.toMQttString(appVersion).length;
		return length + PING_HEADER_SIZE;
	}
	
	@Override
	protected void readMessage(InputStream in, int msgLength)
			throws IOException {
		if(msgLength <=0){
			return;
		}
		DataInputStream dis = new DataInputStream(in);
		byte cFlags = dis.readByte();
		hasAppVersion = (cFlags & 0x04) > 0;
		hasJsonMessages = (cFlags & 0x02) > 0;
		pingMessageId = dis.readUTF();
		if(hasAppVersion){
			appVersion = dis.readUTF();
		}
		if(hasJsonMessages){
			jsonMessages = dis.readUTF();
		}
	}

	@Override
	protected void writeMessage(OutputStream out) throws IOException {
		int flags = hasJsonMessages ? 0x02 : 0;
		flags |= hasAppVersion ? 0x04 : 0;
		DataOutputStream dos = new DataOutputStream(out);
		dos.write((byte) flags);
		dos.writeUTF(pingMessageId);
		if(hasAppVersion){
			dos.writeUTF(appVersion);
		}
		if(hasJsonMessages){
			dos.writeUTF(jsonMessages);
		}
		dos.flush();
	}
	
	@Override
	public void setDup(boolean dup) {
		throw new UnsupportedOperationException("PINGREQ message does not support the DUP flag");
	}
	
	@Override
	public void setQos(QoS qos) {
		throw new UnsupportedOperationException("PINGREQ message does not support the QoS flag");
	}
	
	@Override
	public void setRetained(boolean retain) {
		throw new UnsupportedOperationException("PINGREQ message does not support the RETAIN flag");
	}

	
	public String getPingMessageId() {
		return pingMessageId;
	}

	public void setPingMessageId(String pingMessageId) {
		this.pingMessageId = pingMessageId;
	}

	public String getJsonMessages() {
		return jsonMessages;
	}

	public void setJsonMessages(String jsonMessages) {
		this.jsonMessages = jsonMessages;
		hasJsonMessages = true;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
		hasAppVersion = true;
	}
	
}
