package com.chat.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取本机IP操作工具类
 */
public class IPUtil {

    /**
     * 获取本机IP
     * @return 返回String类型IP地址
     */
    public static String getLocalIPAddr() {
        InetAddress addr = null;
        String ip = "127.0.0.1";
        try {
            addr = InetAddress.getLocalHost();
            ip=addr.getHostAddress().toString();//获得本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            return ip;
        }
    }
}
