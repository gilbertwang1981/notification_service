package com.vip.notifsvr.mqtt.proto;

public enum QoS {
	AT_MOST_ONCE  (0), //最多分发一次
	AT_LEAST_ONCE (1), // 至少分发一次
	EXACTLY_ONCE  (2); //只分发一次
	
	final public int val;
	
	QoS(int val) {
		this.val = val;
	}
	
	static QoS valueOf(int i) {
		for(QoS q: QoS.values()) {
			if (q.val == i){
				return q;
			}
		}
		throw new IllegalArgumentException("Not a valid QoS number: " + i);
	}
}
