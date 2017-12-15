package com.vip.notifsvr.codec;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.vip.notifsvr.mqtt.proto.Message;
import com.vip.notifsvr.mqtt.proto.MessageInputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NsMessageDecoder extends ByteToMessageDecoder {
	
    private byte[] getPayload(int lengthSize, int msgLength, ByteBuf buf) {
        byte[] data = new byte[1 + lengthSize + msgLength];

        buf.resetReaderIndex();
        buf.readBytes(data);

        return data;
    }
	
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() < 2) {
            return;
        }

        buf.markReaderIndex();
        buf.readByte();
        
        int msgLength = 0;
        int multiplier = 1;
        byte digit;
        int counter = 0;
        
        do {
        	counter ++;
            digit = buf.readByte();
            msgLength += (digit & 0x7f) * multiplier;
            multiplier *= 128;
            if ((digit & 0x80) > 0 && !buf.isReadable()) {
                buf.resetReaderIndex();
                return;
            }
        } while ((digit & 0x80) > 0);

        if (buf.readableBytes() < msgLength) {
            buf.resetReaderIndex();
            return;
        }
        
        parse(out , this.getPayload(counter , msgLength, buf));
    }
    
    private void parse(List<Object> out, byte[] data) throws IOException {
        MessageInputStream mis = null;
        try {
            mis = new MessageInputStream(new ByteArrayInputStream(data));
            Message msg = mis.readMessage();
            out.add(msg);
        } finally {
            if (mis != null) {
                mis.close();
            }
        }
    }
}
