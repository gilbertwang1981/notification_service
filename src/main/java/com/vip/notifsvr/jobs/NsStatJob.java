package com.vip.notifsvr.jobs;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.redis.RedisMgr;

public class NsStatJob extends Thread {
	private static Logger logger = LoggerFactory.getLogger(NsStatJob.class);
	
	private static NsStatJob instance = null;
	
	public static NsStatJob getInstance(){
		if (instance == null) {
			instance = new NsStatJob();
		}
		
		return instance;
	}
	
	public void run(){
		while (true) {
			try {
				Thread.sleep(NsConstDefinition.MAX_NS_STAT_JOB_INTERVAL);
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_ACTIVE_DEVICE_NAME + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						NotifyDeviceMgr.getInstance().getActiveDevice().toString());
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_FAIL_CTR_NAME + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						NotifyDeviceMgr.getInstance().getStatCtr(false).toString());
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_SUCCESS_CTR_NAME + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						NotifyDeviceMgr.getInstance().getStatCtr(true).toString());
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_ONLINE_HC + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						Long.toString(System.currentTimeMillis()));
			} catch (Exception e) {
				logger.error("run [NsStatJob] Job failed:" + e.getMessage());
			}
		}
	}
}
