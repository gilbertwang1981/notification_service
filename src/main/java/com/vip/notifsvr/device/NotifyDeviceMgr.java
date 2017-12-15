package com.vip.notifsvr.device;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.redis.RedisMgr;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;

public class NotifyDeviceMgr {
	private static Logger logger = LoggerFactory.getLogger(NotifyDeviceMgr.class);
	
	private static NotifyDeviceMgr instance = null;
	
	private ConcurrentHashMap<String , NotifyDevice> devices = new ConcurrentHashMap<String , NotifyDevice>();
	
	private AtomicLong successCtr = new AtomicLong(0);
	private AtomicLong failCtr = new AtomicLong(0);
	
	private AtomicLong onlineCtr = new AtomicLong(0);
	
	public static NotifyDeviceMgr getInstance(){
		if (instance == null) {
			instance = new NotifyDeviceMgr();
		}
		
		return instance;
	}
	
	public void online() {
		if (onlineCtr.incrementAndGet() > (Long.MAX_VALUE - 1)) {
			onlineCtr.set(0);
		}
	}
	
	public void offline(){
		if (onlineCtr.decrementAndGet() <= 0) {
			onlineCtr.set(0);
		}
	}
	
	public Long getOnlineCtr() {
		return onlineCtr.get();
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
		if (!devices.containsKey(device.getDeviceToken())) {
			devices.put(device.getDeviceToken() , device);
		} else {
			devices.replace(device.getDeviceToken() , device);
		}
		
		try {
			if (!RedisMgr.getInstance().set(device.getDeviceToken() , Integer.toString(device.getPnsId()))){
				logger.error("un-register the device token from the redis failed." + device.getDeviceToken());
				
				return false;
			}
			
			logger.info("register device successfully." + device.getDeviceToken());
		} catch (Exception e) {
			logger.error("register device token into redis failed." + e.getMessage());
			
			return false;
		}
		
		online();
		
		return true;
	}
	
	public void removeDevice(String deviceToken){
		if (StringUtil.isNullOrEmpty(deviceToken)) {
			return;
		}
		
		if (!RedisMgr.getInstance().del(deviceToken)){
			logger.error("un-register the device token from the redis failed." + deviceToken);
		}
		
		if (devices.remove(deviceToken) == null) {
			logger.info("can not find the device in the memory map." + deviceToken);
		}
		
		offline();
		
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
