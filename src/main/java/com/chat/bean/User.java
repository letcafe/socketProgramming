package com.chat.bean;

import com.chat.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    int id;
    String ipAddress;
    String userName;
    String nickName;

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
