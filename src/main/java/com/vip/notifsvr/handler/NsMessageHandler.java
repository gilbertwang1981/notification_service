package com.vip.notifsvr.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.config.NsConfigMgr;
import com.vip.notifsvr.consts.NsChannelAttrKey;
import com.vip.notifsvr.device.NotifyDevice;
import com.vip.notifsvr.device.NotifyDeviceMgr;
import com.vip.notifsvr.mqtt.message.processer.ProcessorMgr;
import com.vip.notifsvr.mqtt.proto.Message;

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
    	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
    	
    	NotifyDeviceMgr.getInstance().removeDevice(key.get());
    	
    	ctx.channel().close();
    	
        super.exceptionCaught(ctx,cause);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		if (!ProcessorMgr.getInstance().execute(msg , ctx)){
			if (ctx.channel() != null && !ctx.channel().isActive()) {
				logger.error("channel will be closed by server," + ctx.channel().toString());
				
				Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
		    	
		    	NotifyDeviceMgr.getInstance().removeDevice(key.get());
				
				ctx.channel().close();
			}
		}
	}
	
	@Override  
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
            IdleStateEvent event = (IdleStateEvent) evt;  
            if (event.state() == IdleState.ALL_IDLE && ctx.channel() != null) {
            	
            	if (ctx.channel() == null) {
            		return;
            	}
            	
            	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
            	
            	NotifyDevice device = NotifyDeviceMgr.getInstance().getDevice(key.get());
            	if (device == null) {
            		return;
            	}
            	
            	if (NsConfigMgr.getInstance().getConfig().getTcpMaxIdle() > 
            				(System.currentTimeMillis() - device.getLastUpdateTime())) {
            		return;
            	}
            	
            	logger.error("write/read idle handler has been triggered, the channel [" 
            			+ ctx.channel().toString() + "] will be closed.");
            	
            	NotifyDeviceMgr.getInstance().removeDevice(key.get());
            	
            	ctx.channel().close();
            }
        }
	}
	
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
    	
    	NotifyDeviceMgr.getInstance().removeDevice(key.get());
        
        ctx.channel().close();
    }
}
