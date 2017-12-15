package com.vip.notifsvr.mqtt.proto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HeartBeat {

    private String deviceToken;
    private String appVersion;
    private Date time;
    private List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Map<String, Object>> getMessages() {
        return new ArrayList<Map<String, Object>>(messages);//2016-07-14 ron.liu
    }

    public void setMessages(List<Map<String, Object>> messages) {
        this.messages.addAll(messages);
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "HeartBeat [deviceToken=" + deviceToken + ", appVersion="
                + appVersion + ", time=" + time + ", messages=" + messages
                + "]";
    }

}
