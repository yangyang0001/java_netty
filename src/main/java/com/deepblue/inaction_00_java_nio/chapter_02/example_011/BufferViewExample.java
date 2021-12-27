package com.deepblue.inaction_00_java_nio.chapter_02.example_011;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 *
 */
public class BufferViewExample {

    public static void main(String[] args) {

        ByteBuffer source = ByteBuffer.allocate(100);
        source.put((byte) 'A');
        source.put((byte) 'B');
        source.put((byte) 'C');
        source.put((byte) 'D');
        source.put((byte) 'E');

        source.flip();

        CharBuffer target = source.asCharBuffer();
        while(target.hasRemaining()) {
            System.out.println(target.get());
        }


    }
}
