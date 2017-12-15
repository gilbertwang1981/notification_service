package com.vip.notifsvr.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.config.NsConfigMgr;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisMgr {
	private static Logger logger = LoggerFactory.getLogger(RedisMgr.class);
	
	private static RedisMgr instance = null;
	
	private JedisCluster redisCluster = null;
	
	public static RedisMgr getInstance(){
		if (instance == null) {
			instance = new RedisMgr();
		}
		
		return instance;
	}
	
	private GenericObjectPoolConfig createRedisConfig() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		
		config.setBlockWhenExhausted(false);
		config.setJmxEnabled(true);
		config.setMaxIdle(NsConfigMgr.getInstance().getConfig().getRedisMaxIdle());
		config.setMaxTotal(NsConfigMgr.getInstance().getConfig().getRedisMaxTotal());
		config.setMaxWaitMillis(NsConfigMgr.getInstance().getConfig().getRedisMaxWait());
		config.setMinEvictableIdleTimeMillis(NsConfigMgr.getInstance().getConfig().getRedisMinEvicaIdleTime());
		config.setMinIdle(NsConfigMgr.getInstance().getConfig().getRedisMinIdle());
		config.setTestOnBorrow(false);
		config.setTestOnCreate(false);
		config.setTestWhileIdle(true);
		config.setMinEvictableIdleTimeMillis(NsConfigMgr.getInstance().getConfig().getRedisMinEvicaIdleTime());
		
		return config;
	}
	
	private Set<HostAndPort> getHosts(){
		Set<HostAndPort> hosts = new HashSet<HostAndPort>();
		
		StringTokenizer tokenizer = new StringTokenizer(NsConfigMgr.getInstance().getConfig().getRedisAddress() , ";");
		while (tokenizer.hasMoreTokens()) {
			String addr = tokenizer.nextToken();
			
			StringTokenizer st = new StringTokenizer(addr , ":");
			String ip = "127.0.0.1";
			int port = 6379;
			if (st.hasMoreTokens()) {
				ip = st.nextToken();
			}
			
			if (st.hasMoreTokens()) {
				port = Integer.parseInt(st.nextToken());
			}
			
			HostAndPort hp = new HostAndPort(ip , port);
			
			hosts.add(hp);
		}
		
		logger.info("add redis cluster node:" + hosts.toString());
		
		return hosts;
	}
	
	public boolean initialize(){
		Set<HostAndPort> hosts = getHosts();
		if (hosts.isEmpty()) {
			return false;
		}
		
		redisCluster = new JedisCluster(hosts, createRedisConfig());
		if (redisCluster == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean set(String key , String value , Integer milliseconds) {
		try {
			redisCluster.psetex(key, milliseconds , value);
			
			return true;
		} catch (Exception e) {
			logger.error("redis cluster set exception:" + e.getMessage());
			
			return false;
		} 
	}
	
	public boolean set(String key , String value) {
		try {
			redisCluster.set(key, value);
			
			return true;
		} catch (Exception e) {
			logger.error("redis cluster set exception:" + e.getMessage());
			
			return false;
		} 		
	}
	
	public String get(String key) {
		try {
			return redisCluster.get(key);
		} catch (Exception e) {
			logger.error("redis cluster get exception:" + e.getMessage());
			
			return null;
		}
	}
	
	public boolean del(String key) {
		try {
			redisCluster.del(key);
			
			return true;
		} catch (Exception e) {
			logger.error("redis cluster del exception:" + e.getMessage());
			
			return false;
		}
	}
	
	public boolean isExisted(String key) {
		try {
			return redisCluster.exists(key);
		} catch (Exception e) {
			logger.error("redis cluster exists exception:" + e.getMessage());
			
			return false;
		}
	}
}
