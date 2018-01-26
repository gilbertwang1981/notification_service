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
				
				Integer activeDevices = NotifyDeviceMgr.getInstance().getActiveDevice();
				Long successCtr = NotifyDeviceMgr.getInstance().getStatCtr(true);
				Long failedCtr = NotifyDeviceMgr.getInstance().getStatCtr(false); 
				Long acksCtr = NotifyDeviceMgr.getInstance().getAcksCtr();
				Long currentTimestamp = System.currentTimeMillis();
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_ACTIVE_DEVICE_NAME + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						activeDevices.toString());
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_FAIL_CTR_NAME + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						failedCtr.toString());
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_SUCCESS_CTR_NAME + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						successCtr.toString());
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_PUBCOMP_NAME + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						acksCtr.toString());
				
				RedisMgr.getInstance().set(NsConstDefinition.CTR_ONLINE_HC + "_" + 
						InetAddress.getLocalHost().getHostName() , 
						Long.toString(currentTimestamp));
				
				logger.info("active devices:{} , success:{} , failed:{} , pubcomp:{} , current timestamp:{}" , 
						activeDevices , successCtr , failedCtr , acksCtr , currentTimestamp);
			} catch (Exception e) {
				logger.error("run [NsStatJob] Job failed:" + e);
			}
		}
	}
}
