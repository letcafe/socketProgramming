package com.chat.dao;

import com.chat.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User Dao,用户数据库访问Model
 */
public class User {

    int id;
    String ipAddress;
    String userName;
    String nickName;

    /**
     * Constructor:构造一个新用户，并在数据库中读取信息，仅触发于本地窗体加载时一次
     * @param ipAddress 数据库中的IP地址
     */
    public User(String ipAddress) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try{
            String sql = "SELECT * FROM user WHERE ipAddress=?";
            conn = ConnectionFactory.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ipAddress);
            resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.ipAddress = resultSet.getString("ipAddress");
                this.userName = resultSet.getString("userName");
                this.nickName = resultSet.getString("nickName");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("User has not been registered");
        } finally {
            try {
                resultSet.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }
}
