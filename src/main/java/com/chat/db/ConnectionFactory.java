package com.chat.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static 	Connection conn;

    private static String driverName = null;

    private static String url = null;

    private static String loginname = null;

    private static String password = null;

    static{
        driverName = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost/socketchat?useUnicode=true&characterEncoding=utf-8";//server ip:192.168.2.196
        loginname = "root";//server name:root
        password = "123456";//server password:tanktju
    }

    private ConnectionFactory(){}


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
