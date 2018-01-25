package com.vip.notifsvr.device;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vip.notifsvr.config.NsConfigMgr;
import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.domain.NsNotifyObject;
import com.vip.notifsvr.kafka.KafkaMgr;
import com.vip.notifsvr.redis.RedisMgr;
import com.vip.notifsvr.util.NsDLockUtil;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;

public class NotifyDeviceMgr {
	private static Logger logger = LoggerFactory.getLogger(NotifyDeviceMgr.class);
	
	private static NotifyDeviceMgr instance = null;
	
	private ConcurrentHashMap<String , NotifyDevice> devices = new ConcurrentHashMap<String , NotifyDevice>();
	
	private AtomicLong successCtr = new AtomicLong(0);
	private AtomicLong failCtr = new AtomicLong(0);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public static NotifyDeviceMgr getInstance(){
		if (instance == null) {
			instance = new NotifyDeviceMgr();
		}
		
		return instance;
	}
	
	public void online(String deviceToken) {
		NsNotifyObject notify = new NsNotifyObject();
		notify.setDeviceToken(deviceToken);
		notify.setTimestamp(System.currentTimeMillis());
		
		try {
			if (!KafkaMgr.getInstance().publish(
					NsConfigMgr.getInstance().getConfig().getLoginTopic() , mapper.writeValueAsString(notify))) {
				logger.warn("notify login event failed." + deviceToken);
			}
		} catch (Exception e) {
			logger.warn("Json exception:" + e);
		}
	}
	
	public void offline(String deviceToken){		
		NsNotifyObject notify = new NsNotifyObject();
		notify.setDeviceToken(deviceToken);
		notify.setTimestamp(System.currentTimeMillis());
		
		try {
			if (!KafkaMgr.getInstance().publish(
					NsConfigMgr.getInstance().getConfig().getLogoutTopic() , mapper.writeValueAsString(notify))) {
				logger.warn("notify logout event failed." + deviceToken);
			}
		} catch (Exception e) {
			logger.warn("Json exception:" + e);
		}
	}
	
	public void increase(boolean isSuccess){
		if (isSuccess) {
			if (successCtr.incrementAndGet() > (Long.MAX_VALUE - 1)) {
				successCtr.set(0);
			}
		} else {
			if (failCtr.incrementAndGet() > (Long.MAX_VALUE - 1)) {
				failCtr.set(0);
			}
		}
	}
	
	public Long getStatCtr(boolean isSuccess) {
		if (isSuccess) {
			return successCtr.get();
		} else {
			return failCtr.get();
		}
	}
	
	public boolean registerDevice(NotifyDevice device){
		if (!NsDLockUtil.getInstance().lock(NsConstDefinition.NS_DLOCK_PREFIX + device.getDeviceToken() ,  
				NsConstDefinition.NS_DLOCK_MAX_EXPIRED)){
			logger.warn("register - Got lock failed. close socket, app will be reconnected." + device.getDeviceToken());
			
			return false;
		}
		
		if (devices.containsKey(device.getDeviceToken())) {
			try {
				devices.get(device.getDeviceToken()).getChannel().close();
			} catch (Exception e) {
				logger.warn("close socket failed." + e);
			}
			
			if (!NsDLockUtil.getInstance().unlock(NsConstDefinition.NS_DLOCK_PREFIX + device.getDeviceToken())){
				logger.warn("register device - unlock failed." + device.getDeviceToken());
			}
			
			return false;
		}
		
		if (!RedisMgr.getInstance().set(device.getDeviceToken() , Integer.toString(device.getPnsId()))){
			logger.warn("register the device token from the redis failed." + device.getDeviceToken());
			
			if (!NsDLockUtil.getInstance().unlock(NsConstDefinition.NS_DLOCK_PREFIX + device.getDeviceToken())){
				logger.warn("register device - unlock failed." + device.getDeviceToken());
			}
			
			return false;
		}
		
		if (!devices.containsKey(device.getDeviceToken())) {
			devices.put(device.getDeviceToken() , device);
		} else {
			devices.replace(device.getDeviceToken() , device);
		}
		
		if (!NsDLockUtil.getInstance().unlock(NsConstDefinition.NS_DLOCK_PREFIX + device.getDeviceToken())){
			logger.warn("register - unlock failed." + device.getDeviceToken());
		}
		
		logger.info("register device successfully." + device.getDeviceToken());
		
		return true;
	}
	
	public void removeDevice(String deviceToken){
		if (StringUtil.isNullOrEmpty(deviceToken)) {
			return;
		}
		
		if (!NsDLockUtil.getInstance().lock(NsConstDefinition.NS_DLOCK_PREFIX + 
				deviceToken,  NsConstDefinition.NS_DLOCK_MAX_EXPIRED)){
			logger.warn("un-register - Got lock failed." + deviceToken);
		}
		
		if (devices.remove(deviceToken) == null) {
			logger.warn("can not find the device in the memory map." + deviceToken);
		}
		
		if (!NsDLockUtil.getInstance().unlock(NsConstDefinition.NS_DLOCK_PREFIX + deviceToken)){
			logger.warn("un-register - unlock failed." + deviceToken);
		}
		
		logger.info("un-register device successfully." + deviceToken);
	}
	
	public void updateLastUpdateTime(String deviceToken) {
		if (StringUtil.isNullOrEmpty(deviceToken)) {
			return;
		}
		
		NotifyDevice device = devices.get(deviceToken);
		if (device != null) {
			device.setLastUpdateTime(System.currentTimeMillis());
		}
	}
	
	public Channel getChannel(String deviceToken) {
		if (StringUtil.isNullOrEmpty(deviceToken)) {
			return null;
		}
		
		NotifyDevice device = devices.get(deviceToken);
		if (device == null) {
			return null;
		}
		
		return device.getChannel();
	}
	
	public NotifyDevice getDevice(String deviceToken) {
		if (!StringUtil.isNullOrEmpty(deviceToken)) {
			return devices.get(deviceToken);
		}
		
		return null;
	}
	
	public Integer getActiveDevice(){
		return devices.size();
	}
}
