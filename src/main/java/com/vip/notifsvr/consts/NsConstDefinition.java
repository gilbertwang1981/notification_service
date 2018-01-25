package com.vip.notifsvr.consts;

public interface NsConstDefinition {
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
	
	public static final String CTR_ONLINE_HC = "ns_counter_online_health_check";
	
	public static final String REPORT_KAFKA_TOPIC_NAME = "reportUMC";
	
	public static final String LOGIN_KAFKA_TOPIC_NAME = "loginTopic";
	
	public static final String LOGOUT_KAFKA_TOPIC_NAME = "logoutTopic";
	
	public static final String KAFKA_KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
	
	public static final String KAFKA_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
	
	public static final String KAFKA_NEED_ACK = "0";
	
	public static final Integer KAFKA_SENDER_RETRY_TIMES = 3;
	
	public static final Integer KAFKA_BATCH_SIZE = 16384;
	
	public static final Integer KAFKA_LINGER_TIME = 1;
	
	public static final Long KAFKA_MEM_SIZE = 33554432L;
	
	public static final Integer HTTP_CLIENT_TIMEOUT = 200;
	
	public static final String NS_MOBILE_TYPE_ANDRIOD = "2";
	
	public static final Integer NS_MOBILE_FEEDBACK_STATUS_SUCCESS = 2;
	
	public static final String NS_DLOCK_CONST_VALUE = "NSDL";
	
	public static final Long NS_DLOCK_WAIT_TIMEOUT = 500L;
	
	public static final Integer NS_DLOCK_MAX_RETRY_TIMES = 5;
	
	public static final Integer NS_DLOCK_MAX_EXPIRED = 5;
	
	public static final String NS_DLOCK_PREFIX = "NS_DLOCK";
	
	public static final Integer NS_IDLE_CHECKER_TIMEOUT = 900;
}
