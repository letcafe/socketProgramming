package com.chat.server;

import com.chat.dao.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 局域网Server端，监听端口9001
 */
public class ChatServer {

    //Server端监听端口号
    static final int SOCKET_LISTENING_PORT = 9001;
    //所有连入Server端的机器信息List,用于消息广播中Iterator迭代发送消息
    ArrayList clientOutputStreams;

    public class ClientHandler implements Runnable {

        BufferedReader reader;
        Socket socket;
        User user;
        String clientIpAddress;

        @Override
        public void run() {
            String message;
            try{
                while ((message = reader.readLine()) != null) {
                    message = new String(message.getBytes(),"utf-8");//解除传至客户端中文乱码
                    System.out.println("read:[" + user.getId() + "," + user.getUserName()
                            + "," + user.getNickName() + "]" + message);
                    tellEveryone(user.getNickName() + " (" + user.getUserName() + ") - from ip "
                            + clientIpAddress + "\n    " + message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Server监听处理函数，当收到Socket请求建立连接时，对连接信息进行初始化
         * @param clientSocket
         */
        public ClientHandler(Socket clientSocket) {
            try {
                socket = clientSocket;
                clientIpAddress = clientSocket.getRemoteSocketAddress().toString();
                clientIpAddress = getPureIpAddress(clientIpAddress);
                user = new User(clientIpAddress);

                InputStreamReader isReader = new InputStreamReader(socket.getInputStream(), "utf-8");//解除接受客户端中文乱码
                reader = new BufferedReader(isReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取加工后的IP地址，对String进行加工，返回纯净IP（Pure IP Address）
         * @param clientIpAddress
         * @return
         */
        private String getPureIpAddress(String clientIpAddress) {
            int indexDoublePoint = clientIpAddress.indexOf(":");
            return clientIpAddress.substring(1, indexDoublePoint);
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }

    /**
     * 启用多线程，迭代向每个Client发送消息
     */
    private void start() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(SOCKET_LISTENING_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getRemoteSocketAddress());
                PrintWriter writer =  new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
                        "utf-8")); //UTF-8格式流向客户端写入
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
