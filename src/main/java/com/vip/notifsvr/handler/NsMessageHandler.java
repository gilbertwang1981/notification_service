package com.vip.notifsvr.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vip.notifsvr.consts.NsChannelAttrKey;
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
    	if (key != null && key.get() != null) {
	    	logger.error("exception caught, the channel [target:" 
					+ ctx.channel().id().asLongText() + " source channel:" + 
					NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText()
					+ "] could be closed." + cause);
	    	
	    	if (NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText().equals(
					ctx.channel().id().asLongText())) {
				NotifyDeviceMgr.getInstance().removeDevice(key.get());
			}
    	}
    	
    	ctx.channel().close();
    	
        super.exceptionCaught(ctx,cause);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		if (!ProcessorMgr.getInstance().execute(msg , ctx)){
			if (ctx.channel() != null) {
				Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);				
				if (key != null && key.get() != null) {
					logger.error("server handling error, the channel [target:" 
							+ ctx.channel().id().asLongText() + " source:" + 
							NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText()
							+ "] will be closed.");
			    	
					if (NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText().equals(
							ctx.channel().id().asLongText())) {
						NotifyDeviceMgr.getInstance().removeDevice(key.get());
					}
				}
				
				ctx.channel().close();
			}
		}
	}
	
	@Override  
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
            IdleStateEvent event = (IdleStateEvent) evt;  
            if (event.state() == IdleState.ALL_IDLE && ctx.channel() != null) {
            	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
            	if (key != null && key.get() != null) {
					logger.warn("write/read idle handler has been triggered, the channel [target:" 
							+ ctx.channel().id().asLongText() + " source:" + 
							NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText()
							+ "] will be closed.");
					
					if (NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText().equals(
							ctx.channel().id().asLongText())) {
						NotifyDeviceMgr.getInstance().removeDevice(key.get());
					}
            	}
				
				ctx.channel().close();
            }
        }
	}
	
	@Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    	Attribute<String> key = ctx.channel().attr(NsChannelAttrKey.DT_CHANNEL_KEY);
    	if (key != null && key.get() != null) {
	    	logger.warn("handle removed, the channel [target:" 
					+ ctx.channel().id().asLongText() + " source channel:" + 
					NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText()
					+ "] could be closed.");
	    	
	    	if (NotifyDeviceMgr.getInstance().getChannel(key.get()).id().asLongText().equals(
					ctx.channel().id().asLongText())) {
				NotifyDeviceMgr.getInstance().removeDevice(key.get());
			}
    	}
        
        ctx.channel().close();
    }
}
