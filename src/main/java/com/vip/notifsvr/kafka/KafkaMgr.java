package com.vip.notifsvr.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.config.NsConfigMgr;
import com.vip.notifsvr.consts.NsConstDefinition;

public class KafkaMgr {
	private static Logger logger = LoggerFactory.getLogger(KafkaMgr.class);
	
	private static KafkaMgr instance = null;
	
	private Producer<String, String> producer = null;
	
	public static KafkaMgr getInstance(){
		if (instance == null) {
			instance = new KafkaMgr();
		}
		
		return instance;
	}
	
	public boolean initialize(){
		Properties props = new Properties(); 
		props.put("bootstrap.servers" , NsConfigMgr.getInstance().getConfig().getBrokers());
		props.put("key.serializer" , NsConstDefinition.KAFKA_KEY_SERIALIZER);  
        props.put("value.serializer" , NsConstDefinition.KAFKA_VALUE_SERIALIZER);
		props.put("acks" , NsConstDefinition.KAFKA_NEED_ACK);
		props.put("retries", NsConstDefinition.KAFKA_SENDER_RETRY_TIMES);
		props.put("batch.size", NsConstDefinition.KAFKA_BATCH_SIZE);  
        props.put("linger.ms", NsConstDefinition.KAFKA_LINGER_TIME);  
        props.put("buffer.memory", NsConstDefinition.KAFKA_MEM_SIZE); 
		
        producer = new KafkaProducer<String , String>(props); 
		
		return true;
	}
	
	public boolean publish(String topic , String message){
		try {
			return producer.send(new ProducerRecord<String , String>(topic , message)).isDone(); 
		} catch (Exception e) {
			logger.error("send to kafka failed", e);
			
			return false;
		}
	}
}
