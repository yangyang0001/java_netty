package com.deepblue.inaction_01_netty_inaction.chapter_04.example_001;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 这个应用程序只简单地接受连接，向客户端 写 "Hello!, 然后关闭连接
 */
public class PlainNioServer {

    public void server() throws IOException {

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ssocket = serverChannel.socket();
        ssocket.bind(new InetSocketAddress(80));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            try {
                selector.select();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                try {
                	if(key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE);
                    }

                	if(key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();

                        byte[] bytes = "Hello!\r\n".getBytes();
                        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
                        buffer.put(bytes);
                        buffer.flip();

                        while(buffer.hasRemaining()) {
                            if(client.write(buffer) == 0) {
                                break;
                            }
                        }
                        client.close();
                    }
                } catch (Exception e) {
                    key.cancel();
                    key.channel().close();
                    e.printStackTrace();
                }
            }
        }



    }
}
