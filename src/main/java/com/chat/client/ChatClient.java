package com.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    //Swing相应组件
    JFrame frame;
    JPanel mainPanel;
    JTextArea comingStream;
    JTextField outputStream;
    JButton sendButton;
    JScrollPane qScroller;

    //客户端连接服务器Socket
    Socket socket;

    //流通道读写对象
    BufferedReader reader;
    PrintWriter writer;


    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.init();
    }

    private void init() {

        setupNetworking();//设置Client端初始链接信息

        Thread readerThread = new Thread(new comingStreamReader());
        readerThread.start();//启动监听，接受数据打印在TextArea中

        setupUerInterface();//设置用户界面以及绑定事件
    }

    private void setupUerInterface() {
        frame = new JFrame("Socket Chat Client [ 本 机 I P : "  + IPUtil.getLocalIPAddr() + " ]");
        mainPanel = new JPanel();
        sendButton = new JButton("发送");
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
        mainPanel.add(outputStream);
        mainPanel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(450, 580);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowClosingMessage());//设置关闭结束线程，并弹出对话确认框
        frame.setVisible(true);
    }


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
