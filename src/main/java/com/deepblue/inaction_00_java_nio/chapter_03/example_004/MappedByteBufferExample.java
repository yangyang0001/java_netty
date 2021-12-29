package com.deepblue.inaction_00_java_nio.chapter_03.example_004;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.OpenOption;

/**
 *
 */
public class MappedByteBufferExample {

    public static void main(String[] args) throws IOException {

        RandomAccessFile accessFile = new RandomAccessFile("/Users/yangjianwei/IdeaProjects/java_netty/src/main/java/com/deepblue/inaction_00_java_nio/chapter_03/example_003/scatter_gather.txt", "r");

        FileChannel channel = accessFile.getChannel();

        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

        while(buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }

    }
}
