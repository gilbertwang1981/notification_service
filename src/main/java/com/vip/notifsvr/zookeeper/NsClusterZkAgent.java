package com.vip.notifsvr.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.config.NsConfigMgr;
import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.util.NsUtil;

public class NsClusterZkAgent implements Watcher {
	private static Logger logger = LoggerFactory.getLogger(NsClusterZkAgent.class);
	
	private static NsClusterZkAgent instance = null;
	
	private ZooKeeper zk = null;
	
	public static NsClusterZkAgent getInstance(){
		if (instance == null) {
			instance = new NsClusterZkAgent();
		}
		
		return instance;
	}

	public void process(WatchedEvent event) {
	}
	
	public boolean connectZk(){		
		try {
			zk = new ZooKeeper(NsConfigMgr.getInstance().getConfig().getZkHost() , 
					NsConfigMgr.getInstance().getConfig().getZkClientTmo() , this);
		} catch (Exception e) {
			logger.error("create zookeeper client failed." + e.getMessage());
			
			return false;
		}
		
		return true;
	}
	
	public boolean register() {
		try {
			if (zk.exists(NsConstDefinition.DEFAULT_ZK_HB_PATH , this) == null) {			
				zk.create(NsConstDefinition.DEFAULT_ZK_HB_PATH , Long.toString(System.currentTimeMillis()).getBytes("UTF-8") , 
						Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
				if (zk.exists(NsConstDefinition.DEFAULT_ZK_HB_PATH + "/" + NsUtil.getLocalHostLANAddress().getHostAddress() , this) == null) {
					zk.create(NsConstDefinition.DEFAULT_ZK_HB_PATH + "/" + NsUtil.getLocalHostLANAddress().getHostAddress() , 
							Long.toString(System.currentTimeMillis()).getBytes("UTF-8") , 
							Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
				}
			}
			
			return true;
		} catch (Exception e) {
			logger.error("register to zk failed." + e.getMessage());
			
			return false;
		}
	}
}
