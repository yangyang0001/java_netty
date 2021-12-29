package com.deepblue.inaction_00_java_nio.chapter_03.example_002;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 *
 */
public class ChannelCopy {

    public static void main(String[] args) throws Exception {

        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel target = Channels.newChannel(System.out);

//        channelCopy1(source, target);

        channelCopy2(source, target);

        source.close();
        target.close();

    }

    private static void channelCopy1(ReadableByteChannel source, WritableByteChannel target) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while(source.read(buffer) != -1) {

            buffer.flip();

            target.write(buffer);

            buffer.compact();
        }

        buffer.flip();

        while(buffer.hasRemaining()) {
            target.write(buffer);
        }

    }

    private static void channelCopy2(ReadableByteChannel source, WritableByteChannel target) throws IOException, InterruptedException {

        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while(source.read(buffer) != 0) {

            buffer.flip();

            target.write(buffer);

            buffer.clear();

            // TODO 验证了 在当前 Channel 上的线程一旦中断, 则 Channel 断开, 并抛出 ClosedByInterruptException
            // Thread.currentThread().interrupt();
        }

    }
}
