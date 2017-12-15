package com.vip.notifsvr.mqtt.message.processer;

import java.io.IOException;

import com.vip.notifsvr.mqtt.proto.Message;

import io.netty.channel.ChannelHandlerContext;


public interface Processer {
    boolean process(Message msg , ChannelHandlerContext ctx) throws IOException ;
}
