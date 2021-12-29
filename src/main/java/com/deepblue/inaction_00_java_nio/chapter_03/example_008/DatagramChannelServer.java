package com.deepblue.inaction_00_java_nio.chapter_03.example_008;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class DatagramChannelServer {

    public static void main(String[] args) {

        try {
            DatagramChannel datagramChannel = DatagramChannel.open();

            datagramChannel.configureBlocking(false);

            datagramChannel.bind(new InetSocketAddress(17));

            while(true) {

                ByteBuffer buffer = ByteBuffer.allocate(1024);

                SocketAddress sa = datagramChannel.receive(buffer);

                if(sa == null) {
                    Thread.sleep(2000L);
                } else {

                    buffer.flip();

                    StringBuffer clientBuffer = new StringBuffer();

                    while(buffer.hasRemaining()) {
                        clientBuffer.append((char) buffer.get());
                    }

                    System.out.println("socket is connect success, socket address is " + sa + ", client say is :" + clientBuffer.toString());

                    buffer.clear();

                    SimpleDateFormat defaultSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

                    ByteBuffer sendBuffer = ByteBuffer.wrap(defaultSDF.format(new Date()).getBytes());

                    datagramChannel.send(sendBuffer, sa);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
