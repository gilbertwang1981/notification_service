package com.vip.notifsvr.consts;

public interface NsConstDefinition {
	public static final String DEFAULT_ZK_HB_PATH = "/nshb";
	
	public static final String DEFAULT_ZK_CLIENT_TIMEOUT = "5000";
	
	public static final Integer REDIS_MAX_IDLE = 10;
	
	public static final Integer REDIS_MIN_IDLE = 3;
	
	public static final Integer REDIS_MAX_TOTAL = 40;
	
	public static final Integer REDIS_MAX_WAIT = 200;
	
	public static final Integer REDIS_MIN_EVI_TIME = 1000;
	
	public static final Integer REDIS_TIME_BETWEEN_EVICATION_RUN = 100;
	
	public static final Integer MAX_NS_STAT_JOB_INTERVAL = 5000;
	
	public static final String CTR_ACTIVE_DEVICE_NAME = "ns_counter_active_device";
	
	public static final String CTR_SUCCESS_CTR_NAME = "ns_counter_success_counter";
	
	public static final String CTR_FAIL_CTR_NAME = "ns_counter_fail_counter";
	
	public static final String CTR_ONLINE_CTR = "ns_counter_online_counter";
	
	public static final String REPORT_KAFKA_TOPIC_NAME = "reportUMC";
	
	public static final String KAFKA_KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
	
	public static final String KAFKA_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
	
	public static final String KAFKA_NEED_ACK = "0";
	
	public static final Integer KAFKA_SENDER_RETRY_TIMES = 3;
	
	public static final Integer KAFKA_BATCH_SIZE = 16384;
	
	public static final Integer KAFKA_LINGER_TIME = 1;
	
	public static final Long KAFKA_MEM_SIZE = 33554432L;
	
	public static final Integer HTTP_CLIENT_TIMEOUT = 200;
}
