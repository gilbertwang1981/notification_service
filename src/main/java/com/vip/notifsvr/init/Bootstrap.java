package com.vip.notifsvr.init;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.notifsvr.codec.NsMessageDecoder;
import com.vip.notifsvr.codec.NsMessageEncoder;
import com.vip.notifsvr.config.NsConfigMgr;
import com.vip.notifsvr.handler.NsMessageHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class Bootstrap {
	private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	public boolean initialize(Integer port) {		
		EventLoopGroup bossGroup = new NioEventLoopGroup(NsConfigMgr.getInstance().getConfig().getBossThreads());
        EventLoopGroup workerGroup = new NioEventLoopGroup(NsConfigMgr.getInstance().getConfig().getWorkerThreads());
        
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addFirst("idleStateHandler", new IdleStateHandler(0, 0, 600));
                pipeline.addLast("decoder", new NsMessageDecoder());
                pipeline.addLast("encoder", new NsMessageEncoder());
                pipeline.addLast("handler", new NsMessageHandler());
            }
        });
        
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true)
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.SO_LINGER, 0)
        .childOption(ChannelOption.SO_SNDBUF, 1024 * 32)
        .childOption(ChannelOption.SO_RCVBUF, 1024 * 20);
        
        bootstrap.option(ChannelOption.SO_BACKLOG, 8192)
        .option(ChannelOption.SO_REUSEADDR, true)
        .option(ChannelOption.SO_RCVBUF, 1024 * 20);
        
        try {
        	logger.info("notification server has been started successfully, bind port:{} boss thread:{} worker thread:{}", 
        			NsConfigMgr.getInstance().getConfig().getServerPort() , NsConfigMgr.getInstance().getConfig().getBossThreads() , 
        			NsConfigMgr.getInstance().getConfig().getWorkerThreads());
        	
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(
            		NsConfigMgr.getInstance().getConfig().getServerPort())).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("notification server error:", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        
		return false;
	}
}
