package com.vip.notifsvr.domain;

public class NsConfig {
	private String tcp_port;
	private String tcp_worker_threads;
	private String tcp_boss_threads;
	private String tcp_max_idle_ms;
	private String id;
	private String mq_host;
	private String mq_port;
	private String mq_user;
	private String mq_pass;
	private String mq_vhost;
	private String mq_queue_prefix;
	private String mq_cthr_num;
	
	public String getMq_host() {
		return mq_host;
	}
	public void setMq_host(String mq_host) {
		this.mq_host = mq_host;
	}
	public String getMq_user() {
		return mq_user;
	}
	public void setMq_user(String mq_user) {
		this.mq_user = mq_user;
	}
	public String getMq_pass() {
		return mq_pass;
	}
	public void setMq_pass(String mq_pass) {
		this.mq_pass = mq_pass;
	}
	public String getMq_vhost() {
		return mq_vhost;
	}
	public void setMq_vhost(String mq_vhost) {
		this.mq_vhost = mq_vhost;
	}
	public String getMq_queue_prefix() {
		return mq_queue_prefix;
	}
	public void setMq_queue_prefix(String mq_queue_prefix) {
		this.mq_queue_prefix = mq_queue_prefix;
	}
	public String getTcp_port() {
		return tcp_port;
	}
	public void setTcp_port(String tcp_port) {
		this.tcp_port = tcp_port;
	}
	public String getTcp_worker_threads() {
		return tcp_worker_threads;
	}
	public void setTcp_worker_threads(String tcp_worker_threads) {
		this.tcp_worker_threads = tcp_worker_threads;
	}
	public String getTcp_boss_threads() {
		return tcp_boss_threads;
	}
	public void setTcp_boss_threads(String tcp_boss_threads) {
		this.tcp_boss_threads = tcp_boss_threads;
	}
	public String getTcp_max_idle_ms() {
		return tcp_max_idle_ms;
	}
	public void setTcp_max_idle_ms(String tcp_max_idle_ms) {
		this.tcp_max_idle_ms = tcp_max_idle_ms;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMq_port() {
		return mq_port;
	}
	public void setMq_port(String mq_port) {
		this.mq_port = mq_port;
	}
	public String getMq_cthr_num() {
		return mq_cthr_num;
	}
	public void setMq_cthr_num(String mq_cthr_num) {
		this.mq_cthr_num = mq_cthr_num;
	}
}
