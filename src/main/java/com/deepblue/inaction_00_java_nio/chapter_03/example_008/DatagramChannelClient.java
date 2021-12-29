package com.deepblue.inaction_00_java_nio.chapter_03.example_008;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *
 */
public class DatagramChannelClient {

    public static void main(String[] args) {

        try {
            DatagramChannel clientChannel = DatagramChannel.open();

            clientChannel.configureBlocking(false);

            ByteBuffer sendBuffer = ByteBuffer.wrap("hello server".getBytes());

            int count = clientChannel.send(sendBuffer, new InetSocketAddress("localhost", 17));

            System.out.println("count is " + count);

            ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

            while(true) {

                SocketAddress receive = clientChannel.receive(receiveBuffer);

                if(receive == null) {
                    Thread.sleep(2000L);
                } else {
                    System.out.println(receive);

                    receiveBuffer.flip();

                    while(receiveBuffer.hasRemaining()) {
                        System.out.print((char) receiveBuffer.get());
                    }

                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
