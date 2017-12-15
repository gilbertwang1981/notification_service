package com.vip.notifsvr.mqtt.proto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Message {
    private static final Logger LOG = LoggerFactory.getLogger(Message.class);

    /**
     * 报文种类<br/>
     * 1.连接请求（CONNECT） :
     * 
     * <pre>
     * 当一个从客户端到服务器的TCP/IP套接字连接被建立时，必须用一个连接流来创建一个协议级别的会话。
     * </pre>
     * 
     * 2.连接请求确认（CONNECTACK）:
     * 
     * <pre>
     * 连接请求确认报文（CONNECTACK）是服务器发给客户端，用以确认客户端的连接请求
     * </pre>
     * 
     * 3.发布报文（PUBLISH）:
     * 
     * <pre>
     * 客户端发布报文到服务器端，用来提供给有着不同需求的订阅者们。每个发布的报文都有一个主题，这是一个分层的命名空间，他定义了报文来源分类，方便订阅者订阅他们需要的主题。
     * 订阅者们可以注册自己的需要的报文类别。
     * </pre>
     * 
     * 4.发布确认报文（PUBACK）:
     * 
     * <pre>
     * 发布确认报文（PUBACK）是对服务质量级别为1的发布报文的应答。他可以是服务器对发布报文的客户端的报文确认，
     * 也可以是报文订阅者对发布报文的服务器的应答。
     * </pre>
     * 
     * 5.发布确认报文（PUBREC）:
     * 
     * <pre>
     * PUBREC报文是对服务质量级别为2的发布报文的应答。这是服务质量级别为2的协议流的第二个报文。
     * PUBREC是由服务器端对发布报文的客户端的应答，或者是报文订阅者对发布报文的服务器的应答。
     * </pre>
     * 
     * 6.发布确认报文（PUBREL):
     * 
     * <pre>
     * PUBREL是报文发布者对来自服务器的PUBREC报文的确认，或者是服务器对来自报文订阅者的PUBREC报文的确认。
     *  它是服务质量级别为2的协议流的第三个报文。
     * </pre>
     * 
     * 7.确定发布完成（PUBCOMP）:
     * 
     * <pre>
     * PUBCOMP报文是服务器对报文发布者的PUBREL报文的应答，或者是报文订阅者对服务器的PUBREL报文的应答。
     * 它是服务质量级别为2的协议流的第四个也是最后一个报文。
     * </pre>
     * 
     * 8.订阅命名的主题（SUBSCRIBE）:
     * 
     * <pre>
     * 订阅报文（SUBSCRIBE）允许一个客户端在服务器上注册一个或多个感兴趣的主题名字。发布给这些主题的报文作为发布报文从服务器端交付给客户端。
     * 订阅报文也描述了订阅者想要收到的发布报文的服务质量等级。
     * </pre>
     * 
     * 9. 订阅报文确认（SUBACK）:
     * 
     * <pre>
     * 当服务器收到客户端发来的订阅报文时，将发送订阅报文的确认报文给客户端。一个这样的确认报文包含一列被授予的服务质量等级。
     * 被授予的服务质量等级次序和对应的订阅报文中的主题名称的次序相符。
     * </pre>
     * 
     * 10. 退订命名的主题(UNSUBSCRIBE):
     * 
     * <pre>
     * 退订主题的报文是从客户端发往服务器端，用以退订命名的主题。
     * </pre>
     * 
     * 11. 退订确认（UNSUBACK）:
     * 
     * <pre>
     * 退订确认报文是从服务器发往客户端，用以确认客户端发来的退订请求报文。
     * </pre>
     * 
     * 12. Ping请求（PINGREQ）:
     * 
     * <pre>
     * Ping请求报文是从连接的客户端发往服务器端，用来询问服务器端是否还存在。
     * </pre>
     * 
     * 13. Ping应答（PINGRESP）:
     * 
     * <pre>
     * Ping应答报文是从服务器端发往Ping请求的客户端，对客户端的Ping请求进行确认。
     * </pre>
     * 
     * 14.（DISCONNECT）:
     * 
     * <pre>
     * 断开通知报文是从客户端发往服务器端用来指明将要关闭它的TCP/IP连接，他允许彻底地断开，而非只是下线。
     * 如果客户端已经和干净会话标志集联系，那么所有先前关于客户端维护的信息将被丢弃。一个服务器在收到断开报文之后，不能依赖客户端关闭TCP/IP连接。
     * </pre>
     */
    public enum Type {
        CONNECT(1), CONNACK(2), PUBLISH(3), PUBACK(4), PUBREC(5), PUBREL(6), PUBCOMP(7), SUBSCRIBE(8), SUBACK(
                9), UNSUBSCRIBE(10), UNSUBACK(11), PINGREQ(12), PINGRESP(13), DISCONNECT(14);

        final private int val;

        Type(int val) {
            this.val = val;
        }

        static Type valueOf(int i) {
            for (Type t : Type.values()) {
                if (t.val == i) {
                    return t;
                }
            }
            return null;
        }
    }

    public static class Header {

        private Type type;
        private boolean retain;
        private QoS qos = QoS.AT_MOST_ONCE;
        private boolean dup;

        private Header(Type type, boolean retain, QoS qos, boolean dup) {
            this.type = type;
            this.retain = retain;
            this.qos = qos;
            this.dup = dup;
        }

        public Header(byte flags) {
            retain = (flags & 1) > 0;
            qos = QoS.valueOf((flags & 0x6) >> 1);
            dup = (flags & 8) > 0;
            type = Type.valueOf((flags >> 4) & 0xF);
        }

        public Type getType() {
            return type;
        }

        private byte encode() {
            byte b = 0;
            b = (byte) (type.val << 4);
            b |= retain ? 1 : 0;
            b |= qos.val << 1;
            b |= dup ? 8 : 0;
            return b;
        }

        @Override
        public String toString() {
            return "Header [type=" + type + ", retain=" + retain + ", qos="
                    + qos + ", dup=" + dup + "]";
        }

    }

    private static char nextId = 1;

    private final Header header;

    public Message(Type type) {
        header = new Header(type, false, QoS.AT_MOST_ONCE, false);
    }

    public Message(Header header) throws IOException {
        this.header = header;
    }

    final void read(InputStream in) throws IOException {
        int msgLength = readMsgLength(in);
        readMessage(in, msgLength);
    }

    public final void write(OutputStream out) throws IOException {
        out.write(header.encode());
        writeMsgLength(out);
        writeMessage(out);
    }

    private int readMsgLength(InputStream in) throws IOException {
        int msgLength = 0;
        int multiplier = 1;
        int digit;
        do {
            digit = in.read();
            msgLength += (digit & 0x7f) * multiplier;
            multiplier *= 128;
        } while ((digit & 0x80) > 0);
        return msgLength;
    }

    private void writeMsgLength(OutputStream out) throws IOException {
        int msgLength = messageLength();
        int val = msgLength;
        do {
            byte b = (byte) (val & 0x7F);
            val >>= 7;
            if (val > 0) {
                b |= 0x80;
            }
            out.write(b);
        } while (val > 0);
    }

    public final byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            write(baos);
        } catch (IOException e) {
            LOG.error("toBytes error:", e);
        }

        return baos.toByteArray();
    }

    protected int messageLength() {
        return 0;
    }

    protected void writeMessage(OutputStream out) throws IOException {
    }

    protected void readMessage(InputStream in, int msgLength) throws IOException {

    }

    public void setRetained(boolean retain) {
        header.retain = retain;
    }

    public boolean isRetained() {
        return header.retain;
    }

    public void setQos(QoS qos) {
        header.qos = qos;
    }

    public QoS getQos() {
        return header.qos;
    }

    public void setDup(boolean dup) {
        header.dup = dup;
    }

    public boolean isDup() {
        return header.dup;
    }

    public Type getType() {
        return header.type;
    }

    public static char nextId() {
        return nextId++;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Message [header=" + header + "]";
    }

}
