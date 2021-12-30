package com.deepblue.inaction_00_java_nio.chapter_04.example_001;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class SelectorExample {

    public static void main(String[] args) {

        try {
            Selector selector = Selector.open();

            ServerSocketChannel channel1 = ServerSocketChannel.open();
            SocketChannel channel2 = SocketChannel.open();
            DatagramChannel channel3 = DatagramChannel.open();

            System.out.println(channel1.validOps());
            System.out.println(channel2.validOps());
            System.out.println(channel3.validOps());

//            channel1.register(selector, SelectionKey.OP_ACCEPT);
//            channel2.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//            channel3.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            System.out.println("---------------------------------------------------------------");

            System.out.println(SelectionKey.OP_ACCEPT);
            System.out.println(SelectionKey.OP_CONNECT);
            System.out.println(SelectionKey.OP_WRITE);
            System.out.println(SelectionKey.OP_READ);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selector.keys();
            selector.selectedKeys();
            // selector.cancel();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if(key.isReadable()) {
                    ReadableByteChannel readChannel = (ReadableByteChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    readChannel.read(buffer);
                }
                SelectableChannel channel = key.channel();
                System.out.println("channel :" + channel);
            }


            selector.select();

        } catch (IOException e) {
            e.printStackTrace();
        }







    }
}
