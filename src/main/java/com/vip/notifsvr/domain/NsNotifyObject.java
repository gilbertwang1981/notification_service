package com.vip.notifsvr.domain;

public class NsNotifyObject {
	private String deviceToken;
	private Long timestamp;
	
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
