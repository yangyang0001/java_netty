package com.deepblue.inaction_00_java_nio.chapter_03.example_006;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 *
 */
public class Channel2Channel {

    public static void main(String[] args) throws IOException {

        String sourcePath = "/Users/yangjianwei/IdeaProjects/java_netty/src/main/java/com/deepblue/inaction_00_java_nio/chapter_03/example_006/source.txt";
        String targetPath = "/Users/yangjianwei/IdeaProjects/java_netty/src/main/java/com/deepblue/inaction_00_java_nio/chapter_03/example_006/target.txt";

        RandomAccessFile accessFile = new RandomAccessFile(sourcePath, "rw");
        FileChannel sourceChannel = accessFile.getChannel();

        FileOutputStream outputStream = new FileOutputStream(targetPath);
        FileChannel targetChannel = outputStream.getChannel();

        sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        // 效果 和 上面的 transferTo 一样!
        // targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());


    }
}
