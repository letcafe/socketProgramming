package com.chat.server;

import com.chat.bean.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServer {
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

        private String getPureIpAddress(String clientIpAddress) {
            int indexDoublePoint = clientIpAddress.indexOf(":");
            return clientIpAddress.substring(1, indexDoublePoint);
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }

    private void start() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(9001);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getRemoteSocketAddress());
                PrintWriter writer =  new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
                        "utf-8"));
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
