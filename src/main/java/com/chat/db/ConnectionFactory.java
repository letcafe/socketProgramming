package com.chat.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 静态工厂，驱动JDBC，链接数据库
 */
public class ConnectionFactory {
    //数据库Basic Information
    private static Connection conn;
    private static String driverName = null;
    private static String url = null;
    private static String loginname = null;
    private static String password = null;

    /**
     * 静态块初始化链接信息，驱动：jdbc for MySQL
     */
    static{
        driverName = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://192.168.2.196/socketchat?useUnicode=true&characterEncoding=utf-8";//server ip:192.168.2.196
        loginname = "root";
        password = "123456";
    }

    private ConnectionFactory(){}

    /**
     * 工厂方法返回数据库Connection对象
     * @return JDBC加载驱动异常以及数据库链接创建异常
     */
    public static Connection getConn(){
        System.out.println("get conn");
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, loginname, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
