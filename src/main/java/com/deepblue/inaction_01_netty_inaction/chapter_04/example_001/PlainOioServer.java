package com.deepblue.inaction_01_netty_inaction.chapter_04.example_001;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.Charset.forName;

/**
 * 这个应用程序只简单地接受连接，向客户端 写 "Hello!, 然后关闭连接
 */
public class PlainOioServer {

    public void server() throws IOException {
        ServerSocket server = new ServerSocket(80);

        try {

            while(true) {
                Socket client = server.accept();
                System.out.println("client connect success, client socket is " + client);

                new Thread(() -> {
                    OutputStream os = null;
                    try {
                        os = client.getOutputStream();
                        os.write("Hello!\r\n".getBytes(forName("UTF-8")));
                        os.flush();
                        client.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            client.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }



    }
}
