package com.deepblue.inaction_00_java_nio.chapter_03.example_007;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 */
public class ServerSocketChannelExample {

    public static final String GREETING = "hello socket, I am server socket!";

    public static void main(String[] args) {

        ServerSocketChannel serverChannel = null;

        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());

        try {
            serverChannel = ServerSocketChannel.open();

            serverChannel.bind(new InetSocketAddress(8080));

            serverChannel.configureBlocking(false);

            while(true) {

                SocketChannel channel = serverChannel.accept();

                if(channel == null) {
                    System.out.println("client channel is null, please wait 2s!");
                    Thread.sleep(2000L);
                } else {
                    System.out.println ("Incoming connection from: " + channel.socket().getRemoteSocketAddress());
                    buffer.rewind();
                    channel.write(buffer);
                    channel.close();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
