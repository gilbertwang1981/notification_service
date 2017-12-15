package com.vip.notifsvr.device;

import io.netty.channel.Channel;

public class NotifyDevice {    
	private String deviceToken;
	private Channel channel;
	private long createTime;
	private long lastUpdateTime;
	private int pnsId;
	
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getPnsId() {
		return pnsId;
	}
	public void setPnsId(int pnsId) {
		this.pnsId = pnsId;
	}
}
