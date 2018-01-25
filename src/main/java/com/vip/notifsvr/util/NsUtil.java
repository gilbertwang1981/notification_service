package com.vip.notifsvr.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NsUtil {

    private static final Logger logger = LoggerFactory.getLogger(NsUtil.class);
    
    public static String dumpByteArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            int iVal = b & 0xFF;
            int byteN = Integer.parseInt(Integer.toBinaryString(iVal));
            sb.append(String.format("%1$02d: %2$08d %3$1c %3$d\n", i, byteN, iVal));
        }
        return sb.toString();
    }

    public static byte[] toMQttString(String s) {
        if (s == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(byteOut);
        try {
            dos.writeUTF(s);
            dos.flush();
        } catch (IOException e) {
            logger.error("exception:",e);

            return new byte[0];
        }
        return byteOut.toByteArray();
    }

    public static String toString(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        try {
            return dis.readUTF();
        } catch (IOException e) {
            logger.error("exception:",e);
        }
        return null;
    }

    public static String formatTimeDuration(long duration) {
        long sec = (duration / 1000) % 60;
        long min = (duration / 1000 / 60) % 60;
        long hour = (duration / 1000 / 60 / 60) % 24;
        long day = duration / 1000 / 60 / 60 / 24;

        if (day > 0) {
            return day + "d" + hour + "h" + min + "m" + sec + "s";
        }

        if (hour > 0) {
            return hour + "h" + min + "m" + sec + "s";
        }

        if (min > 0) {
            return min + "m" + sec + "s";
        }

        return sec + "s";
    }
    
    @SuppressWarnings("rawtypes")
	public static InetAddress getLocalHostLANAddress() throws Exception {
        try {
            InetAddress candidateAddress = null;
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }

            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            
            return jdkSuppliedAddress;
        } catch (Exception e) {
            logger.error("exception happened:" + e.getMessage(), e);
        }
        return null;
    }
}
