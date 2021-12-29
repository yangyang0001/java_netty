package com.deepblue.inaction_00_java_nio.chapter_03.example_001;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Socket 通道永远不会自动创建, 只有在已知有一个通道存在的情况下, 用相应的代码去映射这个真实的通道!
 */
public class ChannelExample {

    public static void main(String[] args) throws IOException {

        // File 通道
        RandomAccessFile accessFile = new RandomAccessFile("", "rw");
        FileChannel fileChannel = accessFile.getChannel();


        // Socket 通道 分为 SocketChannel, ServerSocketChannel, DatagramChannel
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("", 8080));

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("", 8080));

        DatagramChannel datagramChannel = DatagramChannel.open();

    }
}
