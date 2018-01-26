package com.vip.notifsvr.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vip.notifsvr.consts.NsChannelAttrKey;
import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.mqtt.message.processer.ProcessorMgr;
import com.vip.notifsvr.mqtt.proto.Message;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;

public class NsMessageHandler extends SimpleChannelInboundHandler<Message> {
	
	private static Logger logger = LoggerFactory.getLogger(NsMessageHandler.class);
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	try {
	    	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
	    	if (key != null && key.get() != null) {
	    		Channel channel = NotifyDeviceMgr.getInstance().getChannel(key.get());
	    		if (channel != null) {
			    	logger.warn("exception caught, the channel [target:" 
							+ ctx.channel().id().asLongText() + " source channel:" + 
							channel.id().asLongText() + "] could be closed." + cause);
			    	
			    	if (channel.id().asLongText().equals(
							ctx.channel().id().asLongText())) {
						NotifyDeviceMgr.getInstance().removeDevice(key.get());
					}
	    		}
	    	}
    	} catch(Exception e) {
    		throw e;
    	} finally {
    		ctx.channel().close();
    	}
    	
        super.exceptionCaught(ctx,cause);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		if (!ProcessorMgr.getInstance().execute(msg , ctx)){
			if (ctx.channel() != null) {
				try {
					Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);				
					if (key != null && key.get() != null) {
						Channel channel = NotifyDeviceMgr.getInstance().getChannel(key.get());
						if (channel != null) {
							logger.debug("server handling error, the channel [target:" 
									+ ctx.channel().id().asLongText() + " source:" + 
									channel.id().asLongText() + "] will be closed.");
					    	
							if (channel.id().asLongText().equals(
									ctx.channel().id().asLongText())) {
								NotifyDeviceMgr.getInstance().removeDevice(key.get());
							}
						}
					}
				} catch(Exception e) {
		    		throw e;
		    	} finally {
		    		ctx.channel().close();
		    	}
			}
		}
	}
	
	@Override  
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
            IdleStateEvent event = (IdleStateEvent) evt;  
            if (event.state() == IdleState.ALL_IDLE && ctx.channel() != null) {
            	try {
	            	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
	            	if (key != null && key.get() != null) {
	            		Channel channel = NotifyDeviceMgr.getInstance().getChannel(key.get());
	            		if (channel != null) {
							logger.warn("write/read idle handler has been triggered, the channel [target:" 
									+ ctx.channel().id().asLongText() + " source:" + channel.id().asLongText()
									+ "] will be closed.");
							
							if (channel.id().asLongText().equals(
									ctx.channel().id().asLongText())) {
								NotifyDeviceMgr.getInstance().removeDevice(key.get());
							}
	            		}
	            	}
            	} catch(Exception e) {
            		throw e;
            	} finally {
            		ctx.channel().close();
            	}
            }
        }
	}
	
	@Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		try {
	    	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
	    	if (key != null && key.get() != null) {
	    		Channel channel = NotifyDeviceMgr.getInstance().getChannel(key.get());
	    		if (channel != null) {
			    	logger.debug("handle removed, the channel [target:" 
							+ ctx.channel().id().asLongText() + " source channel:" + 
							channel.id().asLongText()
							+ "] could be closed.");
			    	
			    	if (channel.id().asLongText().equals(
							ctx.channel().id().asLongText())) {
						NotifyDeviceMgr.getInstance().removeDevice(key.get());
					}
	    		}
	    	} 
    	} catch(Exception e) {
    		throw e;
    	} finally {
    		ctx.channel().close();
    	}
    }
}
