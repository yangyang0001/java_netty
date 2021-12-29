package com.deepblue.inaction_00_java_nio.chapter_03.example_007;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 *
 */
public class SocketChannelExample {

    public static void main(String[] args) {

        SocketChannel socketChannel = null;

        try {

            socketChannel = SocketChannel.open();

            socketChannel.configureBlocking(false);

            socketChannel.connect(new InetSocketAddress("localhost",8080));

            if(!socketChannel.finishConnect()) {
                System.out.println("connetion is not success!");
            } else {
                System.out.println("connetion is success!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
