package com.vip.notifsvr.domain;

public class NsConfiguration {
	private Integer bossThreads;
	private Integer workerThreads;
	private Integer serverPort;
	private String mqHosts;
	private Integer mqPort;
	private String mqUser;
	private String mqPass;
	private String mqVHost;
	private String queueNamePrefix;
	private Integer pnsId;
	private Integer mqConsumerThreads;
	private String redisAddress;
	private Integer redisMaxIdle;
	private Integer redisMaxTotal;
	private Integer redisMaxWait;
	private Integer redisMinEvicaIdleTime;
	private Integer redisMinIdle;
	private Integer redisTimeBetweenEvicaRun;
	private String zkHost;
	private Integer zkClientTmo;
	private Integer tcpMaxIdle;
	private String brokers;
	
	@Override
	public String toString(){
		return "NS Configuration:\n" + 
				"boos thread:" + this.bossThreads + "\n" +
				"worker thread:" + this.workerThreads + "\n" +
				"server port:" + this.serverPort + "\n" +
				"tcp max idle:" + this.tcpMaxIdle + "\n" +
				"MQ host:" + this.mqHosts + "\n" +
				"MQ port:" + this.mqPort + "\n" +
				"MQ user:" + this.mqUser + "\n" +
				"MQ vhost:" + this.mqVHost + "\n" +
				"MQ queue name prefix:" + this.queueNamePrefix + "\n" +
				"pns id:" + this.pnsId + "\n" +
				"MQ consumer threads:" + this.mqConsumerThreads + "\n" +
				"redis cluster address:" + this.redisAddress + "\n" +
				"redis max idle:" + this.redisMaxIdle + "\n" +
				"redis max total:" + this.redisMaxTotal + "\n" +
				"redis max wait:" + this.redisMaxWait + "\n" +
				"redis min evica idle time:" + this.redisMinEvicaIdleTime + "\n" +
				"redis min idle:" + this.redisMinIdle + "\n" +
				"redis time between evica run:" + this.redisTimeBetweenEvicaRun + "\n" +
				"ZK host:" + this.zkHost + "\n" +
				"ZK timeout:" + this.zkClientTmo + "\n" +
				"KAFKA brokers:" + this.brokers;
	}
	
	public Integer getBossThreads() {
		return bossThreads;
	}
	public void setBossThreads(Integer bossThreads) {
		this.bossThreads = bossThreads;
	}
	public Integer getWorkerThreads() {
		return workerThreads;
	}
	public void setWorkerThreads(Integer workerThreads) {
		this.workerThreads = workerThreads;
	}
	public Integer getServerPort() {
		return serverPort;
	}
	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}
	public String getMqHosts() {
		return mqHosts;
	}
	public void setMqHosts(String mqHosts) {
		this.mqHosts = mqHosts;
	}
	public Integer getMqPort() {
		return mqPort;
	}
	public void setMqPort(Integer mqPort) {
		this.mqPort = mqPort;
	}
	public String getMqUser() {
		return mqUser;
	}
	public void setMqUser(String mqUser) {
		this.mqUser = mqUser;
	}
	public String getMqPass() {
		return mqPass;
	}
	public void setMqPass(String mqPass) {
		this.mqPass = mqPass;
	}
	public String getMqVHost() {
		return mqVHost;
	}
	public void setMqVHost(String mqVHost) {
		this.mqVHost = mqVHost;
	}
	public String getQueueNamePrefix() {
		return queueNamePrefix;
	}
	public void setQueueNamePrefix(String queueNamePrefix) {
		this.queueNamePrefix = queueNamePrefix;
	}
	public Integer getPnsId() {
		return pnsId;
	}
	public void setPnsId(Integer pnsId) {
		this.pnsId = pnsId;
	}
	public Integer getMqConsumerThreads() {
		return mqConsumerThreads;
	}
	public void setMqConsumerThreads(Integer mqConsumerThreads) {
		this.mqConsumerThreads = mqConsumerThreads;
	}
	public Integer getRedisMaxIdle() {
		return redisMaxIdle;
	}
	public void setRedisMaxIdle(Integer redisMaxIdle) {
		this.redisMaxIdle = redisMaxIdle;
	}
	public Integer getRedisMaxTotal() {
		return redisMaxTotal;
	}
	public void setRedisMaxTotal(Integer redisMaxTotal) {
		this.redisMaxTotal = redisMaxTotal;
	}
	public Integer getRedisMaxWait() {
		return redisMaxWait;
	}
	public void setRedisMaxWait(Integer redisMaxWait) {
		this.redisMaxWait = redisMaxWait;
	}
	public Integer getRedisMinEvicaIdleTime() {
		return redisMinEvicaIdleTime;
	}
	public void setRedisMinEvicaIdleTime(Integer redisMinEvicaIdleTime) {
		this.redisMinEvicaIdleTime = redisMinEvicaIdleTime;
	}
	public Integer getRedisMinIdle() {
		return redisMinIdle;
	}
	public void setRedisMinIdle(Integer redisMinIdle) {
		this.redisMinIdle = redisMinIdle;
	}
	public Integer getRedisTimeBetweenEvicaRun() {
		return redisTimeBetweenEvicaRun;
	}
	public void setRedisTimeBetweenEvicaRun(Integer redisTimeBetweenEvicaRun) {
		this.redisTimeBetweenEvicaRun = redisTimeBetweenEvicaRun;
	}
	public String getZkHost() {
		return zkHost;
	}
	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}
	public Integer getZkClientTmo() {
		return zkClientTmo;
	}
	public void setZkClientTmo(Integer zkClientTmo) {
		this.zkClientTmo = zkClientTmo;
	}
	public String getRedisAddress() {
		return redisAddress;
	}
	public void setRedisAddress(String redisAddress) {
		this.redisAddress = redisAddress;
	}

	public Integer getTcpMaxIdle() {
		return tcpMaxIdle;
	}

	public void setTcpMaxIdle(Integer tcpMaxIdle) {
		this.tcpMaxIdle = tcpMaxIdle;
	}

	public String getBrokers() {
		return brokers;
	}

	public void setBrokers(String brokers) {
		this.brokers = brokers;
	}
}
