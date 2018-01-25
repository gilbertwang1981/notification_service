package com.vip.notifsvr.rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.vip.notifsvr.config.NsConfigMgr;
import com.vip.notifsvr.consts.NsConstDefinition;
import com.vip.notifsvr.domain.PubMessage;
import com.vip.notifsvr.domain.PushMessageReport;
import com.vip.notifsvr.kafka.KafkaMgr;
import com.vip.notifsvr.notifier.Publisher;

public class RabbitMqMgr {
	private static RabbitMqMgr instance = null;
	
	private static Logger logger = LoggerFactory.getLogger(RabbitMqMgr.class);
	
	private ObjectMapper mapper = new ObjectMapper(); 
	
	private Integer nodeId = 0;
	
	public static RabbitMqMgr getInstance(){
		if (instance == null) {
			instance = new RabbitMqMgr();
		}
		
		return instance;
	}
	
	public boolean initialize(){
		try {
			ConnectionFactory connFactory = new ConnectionFactory();
			
			connFactory.setHost(NsConfigMgr.getInstance().getConfig().getMqHosts());
			connFactory.setPort(NsConfigMgr.getInstance().getConfig().getMqPort());
			connFactory.setUsername(NsConfigMgr.getInstance().getConfig().getMqUser());
			connFactory.setPassword(NsConfigMgr.getInstance().getConfig().getMqPass());
			connFactory.setVirtualHost(NsConfigMgr.getInstance().getConfig().getMqVHost());
			
			ExecutorService es = Executors.newFixedThreadPool(NsConfigMgr.getInstance().getConfig().getMqConsumerThreads());
			Connection connection = connFactory.newConnection(es);
			
			Channel channel = connection.createChannel();
			channel.queueDeclare(NsConfigMgr.getInstance().getConfig().getQueueNamePrefix() + 
					NsConfigMgr.getInstance().getConfig().getPnsId() , true , false , false , null);
			
			setNodeId(NsConfigMgr.getInstance().getConfig().getPnsId());
			
			Consumer consumer = new DefaultConsumer(channel) {  
	            @Override  
	            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)  
	                    throws IOException {            	
	            	PubMessage obj = mapper.readValue(new String(body, "UTF-8") , PubMessage.class);
	            	
	            	if (!Publisher.getInstance().publish(obj.getDeviceToken() , obj.getContent() , obj.getTopic())){
	            		logger.warn("publish message failed. " + obj.getDeviceToken() + "/" + obj.getContent());
	            	}
	            	
	            	send2Kafka(obj);
	            }  
	        };
	        
        	channel.basicConsume(NsConfigMgr.getInstance().getConfig().getQueueNamePrefix() + 
					NsConfigMgr.getInstance().getConfig().getPnsId() , true, consumer); 
			
			return true;
		} catch (Exception e) {
			logger.error("start the consumer thread failed.", e);
			
			return false;
		}
	}
	
	private void send2Kafka(PubMessage pub) {
		PushMessageReport report = new PushMessageReport();
		
		report.setAppName(pub.getAppName());
		report.setOs(NsConstDefinition.NS_MOBILE_TYPE_ANDRIOD);
		report.setStatus(NsConstDefinition.NS_MOBILE_FEEDBACK_STATUS_SUCCESS);
		report.setPushId(Long.parseLong(pub.getMsgId()));
		report.setCreateTime(new Date(System.currentTimeMillis()));
		report.setLastUpdTime(new Date(System.currentTimeMillis()));
		report.setMsgType(1);
		
		try {
			KafkaMgr.getInstance().publish(NsConfigMgr.getInstance().getConfig().getReportTopic() , 
					mapper.writeValueAsString(report));
		} catch (JsonProcessingException e) {
			logger.error("json parser exception:", e);
			
			return;
		}
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
}
