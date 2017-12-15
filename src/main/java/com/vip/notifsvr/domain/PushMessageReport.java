package com.vip.notifsvr.domain;

import java.util.Date;

enum MsgTypeEnum {
    RCYX("日常营销", 1), 
    YYTZ("应用通知", 2), 
    HDYX("活动营销", 3), 
    QGYX("情感营销", 4), 
    GWCCSTX("购物车超时提醒", 5), 
    PPDY("品牌订阅", 6), 
    WLXX("物流信息", 7), 
    GWCWH("购物车挽回", 8), 
    SCTX("收藏提醒", 9), 
    YHZH("用户召回", 10), 
    DCYR("大促预热", 11), 
    DZCDD("待支付订单", 30), 
    DYYSC("订阅与收藏", 44);

    private String desc;
    private int value;

    private MsgTypeEnum(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public static String getDesc(int value) {
        for (MsgTypeEnum type : MsgTypeEnum.values()) {
            if (type.getValue() == value) {
                return type.desc;
            }
        }
        return null;
    }

    public static MsgTypeEnum findByValue(int value) {
        for (MsgTypeEnum type : MsgTypeEnum.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}


public class PushMessageReport {
    private Long pushId;
    private String appName;
    private String appVersion;
    private String deviceToken;
    private String groupId;
    private String channel; // 接入渠道
    private Integer msgType; // 消息类型

    private Date expiredTime; // 过期时间

    private Integer status; // 消息状态
    private Integer tryCount; // 尝试重发次数

    private Date createTime; // 创建时间

    private Date lastUpdTime; // 最后更新时间

    private String os;
    private String warehouse;
    private String text;
    private String args;
    private String title;

    private String title_color;// 涉及数据上报kafka，改动后会导致数据解析异常
    private String content_color;// 涉及数据上报kafka，改动后会导致数据解析异常
    private String bg_color;// 涉及数据上报kafka，改动后会导致数据解析异常
    private String bg_pic;// 涉及数据上报kafka，改动后会导致数据解析异常
    private String user_id;// 涉及数据上报kafka，改动后会导致数据解析异常

    private Date receivedTime; // 接收时间

    private Date openedTime; // 打开时间

    private Long vipruid;

    private Date showTime; // 显示时间

    private Integer cssType;// android的显示样式
    private String icon; // android的显示icon

    private String msgTypeTag;
    private String statusTag;
    private String content;
    private String regId;
    private String mid;
    private Integer regPlat;// push平台类型，0：唯品会 1：小米 2：个推

    private String createDates;
    private String serialNumber;
    private String sound; // 声音
    private String logo; // 声音

    private String subTitle; // 副标题 since ios 10
    private String attachmentUrl; // media rich content since ios 10
    private Integer contentAvailable;
    private String manufacturer;
    private String os_version;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgTypeTag() {
        msgTypeTag = MsgTypeEnum.getDesc(msgType);
        return msgTypeTag == null ? "" : msgTypeTag;
    }

    public String getStatusTag() {
        switch (status) {
            case 1:
                statusTag = "发送中";
                break;
            case 2:
                statusTag = "发送成功";
                break;
            case 3:
                statusTag = "发送超时";
                break;
            case 4:
                statusTag = "未执行";
                break;

            default:
                statusTag = "";
                break;
        }
        return statusTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_color() {
        return title_color;
    }

    public void setTitle_color(String title_color) {
        this.title_color = title_color;
    }

    public String getContent_color() {
        return content_color;
    }

    public void setContent_color(String content_color) {
        this.content_color = content_color;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getBg_pic() {
        return bg_pic;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Date getOpenedTime() {
        return openedTime;
    }

    public void setOpenedTime(Date openedTime) {
        this.openedTime = openedTime;
    }

    public Long getVipruid() {
        return vipruid;
    }

    public void setVipruid(Long vipruid) {
        this.vipruid = vipruid;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "PushMessageInfo [deviceToken=" + deviceToken + ", appName=" + appName + ", pushId=" + pushId +
                ", status=" + status + ", groupId=" + groupId + ",msgType=" + msgType + ", createTime=" + createTime
                + ", receivedTime=" + receivedTime + ", openedTime=" + openedTime + ", serialNumber=" + serialNumber
                + "]";
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public void setPushId(Long pushId) {
        this.pushId = pushId;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCssType(Integer cssType) {
        this.cssType = cssType;
    }

    public String getCreateDates() {
        return createDates;
    }

    public void setCreateDates(String createDates) {
        this.createDates = createDates;
    }

    public Long getPushId() {
        return pushId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCssType() {
        return cssType;
    }

    public Integer getRegPlat() {
        return regPlat;
    }

    public void setRegPlat(Integer regPlat) {
        this.regPlat = regPlat;
    }

    public String getSound() {
        return sound;
    }

    public String getLogo() {
        return logo;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public Integer getContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(Integer contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }
}
