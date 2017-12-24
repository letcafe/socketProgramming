package com.chat.client;

import com.chat.dao.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 启动局域网聊天客户端。当服务端启动后，运行加入聊天室
 */
public class ChatClient {

    //Swing相应组件
    JFrame frame;
    JPanel mainPanel;
    JTextArea comingStream;
    JTextField outputStream;
    JButton sendButton;
    JScrollPane qScroller;
    JLabel userInfoLabel;

    //客户端连接服务器Socket
    Socket socket;
    User user;

    //流通道读写对象
    BufferedReader reader;
    PrintWriter writer;


    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.init();
    }

    /**
     * 初始化Client函数
     */
    private void init() {
        setupNetworking();//设置Client端初始链接信息

        Thread readerThread = new Thread(new comingStreamReader());
        readerThread.start();//启动监听，接受数据打印在TextArea中

        setupUerInterface();//设置用户界面以及绑定事件
    }

    /**
     * UI相关以及事件绑定
     */
    private void setupUerInterface() {
        user = new User(IPUtil.getLocalIPAddr());//获取用户IP地址，并展示于标题栏
        frame = new JFrame("Socket Chat Client [ 本 机 I P : "  + user.getIpAddress() + " ]");
        userInfoLabel = new JLabel(user.getNickName() + " [ " + user.getUserName() + " ] : ");
        mainPanel = new JPanel();
        sendButton = new JButton("发 送");
        outputStream = new JTextField(20);
        comingStream = new JTextArea(28, 38);
        qScroller = new JScrollPane(comingStream);

        comingStream.setLineWrap(true);
        comingStream.setWrapStyleWord(true);
        comingStream.setEditable(false);
        comingStream.setText("* * * 欢 迎 使 用 Tank 局 域 网 聊 天 程 序 * * *\n"
                + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");

        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        outputStream.addKeyListener(new EnterPressSend(sendButton));//绑定键盘Enter事件
        sendButton.addActionListener(new SendButtonListener(writer, outputStream));//绑定单击事件

        mainPanel.add(qScroller);
        mainPanel.add(userInfoLabel);
        mainPanel.add(outputStream);
        mainPanel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(450, 580);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowClosingMessage());//设置关闭结束线程，并弹出对话确认框
        frame.setVisible(true);
    }

    /**
     * 初始化网络，Socket端尝试链接
     * UnknownHostException： IP出错，一般为网卡不在同一网段
     * IOException： 读写错误，一般产生于读写中断
     */
    private void setupNetworking() {
        try {
            socket = new Socket("192.168.2.196", 9001);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream(),
                    "utf-8");//解除接受服务端中文乱码
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),
                    "utf-8"));//解除传至服务端中文乱码
            System.out.println("Network established");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Socket建立链接后，开启多线程进行数据读取
     */
    private class comingStreamReader implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("read:" + message);
                    comingStream.append(message + "\n");
                    comingStream.setCaretPosition(comingStream.getText().length());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
