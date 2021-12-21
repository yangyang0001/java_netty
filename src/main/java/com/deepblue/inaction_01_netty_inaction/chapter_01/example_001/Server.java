package com.deepblue.inaction_01_netty_inaction.chapter_01.example_001;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        int port = 8080;

        // 阻塞接受客户端的请求
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();

        // 服务端接收到请求后处理请求
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

        String request  = null;
        String response = null;

        while((request = reader.readLine()) != null) {
            // 处理请求
            if("Done".equals(request)){
                break;
            } else {
                response = "request";
            }

            // 处理请求结束后写回client端
            writer.write(response);
        }


    }
}
