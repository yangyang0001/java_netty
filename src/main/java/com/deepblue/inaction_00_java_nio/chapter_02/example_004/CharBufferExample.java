package com.deepblue.inaction_00_java_nio.chapter_02.example_004;

import java.nio.CharBuffer;

/**
 *
 */
public class CharBufferExample {

    public static void main(String[] args) {

        CharBuffer buffer = CharBuffer.allocate(100);

        fillBuffer(buffer);

        buffer.flip();

        drainBuffer(buffer);

        buffer.clear();

    }

    private static void drainBuffer(CharBuffer buffer) {
        while(buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }

    private static void fillBuffer(CharBuffer buffer) {

        String string = "ABCDEFGHIJKLMNOPQRSTWVUXYZ10234567890abcdefghijklmnopqrstwvuxyz";

        for(int i = 0; i < 20; i++) {
            buffer.put(string.charAt(i));
        }


    }
}
