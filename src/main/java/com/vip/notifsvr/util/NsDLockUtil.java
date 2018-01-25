package com.vip.notifsvr.util;

import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.redis.RedisMgr;

public class NsDLockUtil {
	private static NsDLockUtil instance = null;
	
	private ThreadLocal<Integer> retryTimes = new ThreadLocal<Integer>();
	
	public static NsDLockUtil getInstance() {
		if (instance == null) {
			instance = new NsDLockUtil();
		}
		
		return instance;
	}
	
	public boolean lock(String key , Integer timeout) {
		retryTimes.set(0);
		while (retryTimes.get() < NsConstDefinition.NS_DLOCK_MAX_RETRY_TIMES) {
			retryTimes.set(retryTimes.get() + 1);
			if (RedisMgr.getInstance().setnx(key , NsConstDefinition.NS_DLOCK_CONST_VALUE)) {
				if (!RedisMgr.getInstance().expire(key , timeout)){
					RedisMgr.getInstance().del(key);
				}
				return true;
			}
			
			try {
				Thread.sleep(NsConstDefinition.NS_DLOCK_WAIT_TIMEOUT);
			} catch (Exception e) {
				// ignore
			}
		}
		return false;
	}
	
	public boolean unlock(String key) {
		return RedisMgr.getInstance().del(key);
	}
}
