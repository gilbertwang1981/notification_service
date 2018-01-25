package com.vip.notifsvr.domain;

public class NsConfig {
	private String tcp_port;
	private String tcp_worker_threads;
	private String tcp_boss_threads;
	private String tcp_max_idle_ms;
	private String id;
	
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
}
