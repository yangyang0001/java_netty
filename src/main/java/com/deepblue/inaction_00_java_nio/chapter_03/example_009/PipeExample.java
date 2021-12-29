package com.deepblue.inaction_00_java_nio.chapter_03.example_009;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 两块缓冲区 BufferA, BufferB, 中间用 Pipe 连接; 从 BufferA 往外写用 SinkChannel, 从 BufferB 中读取用 SourceChannel
 * BufferA ---> BufferB
 */
public class PipeExample {

    public static void main(String[] args) {

        try {
            Pipe pipe = Pipe.open();

            ByteBuffer sinkBuffer = ByteBuffer.wrap("pipe_channel_data".getBytes());
            ByteBuffer sourBuffer = ByteBuffer.allocate(1024);

            Pipe.SinkChannel sink = pipe.sink();
            Pipe.SourceChannel source = pipe.source();

            sink.write(sinkBuffer);
            source.read(sourBuffer);

            sourBuffer.flip();

            while(sourBuffer.hasRemaining()) {
                System.out.print((char) sourBuffer.get());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
