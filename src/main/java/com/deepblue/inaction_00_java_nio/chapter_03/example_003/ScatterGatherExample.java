package com.deepblue.inaction_00_java_nio.chapter_03.example_003;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 多个 ByteBuffer 进行分散和聚合的操作! 这里是进行聚合的操作, 往文件中写数据!
 */
public class ScatterGatherExample {

    public static String SEPARATOR = System.getProperty ("line.separator");

    public static void main(String[] args) throws IOException {

        FileOutputStream fos = new FileOutputStream("/Users/yangjianwei/IdeaProjects/java_netty/src/main/java/com/deepblue/inaction_00_java_nio/chapter_03/example_003/scatter_gather2.txt");

        ByteBuffer[] buffers = new ByteBuffer[3];

        ByteBuffer buffer0 = ByteBuffer.allocate(10);
        String string0 = "welcome to the java nio" + SEPARATOR;
        buffer0.put(string0.getBytes(), 0, Math.min(string0.length(), buffer0.capacity()));

        ByteBuffer buffer1 = ByteBuffer.allocate(2);
        buffer1.put(SEPARATOR.getBytes());

        ByteBuffer buffer2 = ByteBuffer.allocate(25);
        String string2 = "this is scatter gather" + SEPARATOR;
        buffer2.put(string2.getBytes(), 0, Math.min(string2.length(), buffer2.capacity()));

        buffer0.flip();
        buffer1.flip();
        buffer2.flip();

        buffers[0] = buffer0;
        buffers[1] = buffer1;
        buffers[2] = buffer2;

        FileChannel channel = fos.getChannel();
        channel.write(buffers);


    }
}
