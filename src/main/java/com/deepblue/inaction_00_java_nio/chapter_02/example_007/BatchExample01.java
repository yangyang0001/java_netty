package com.deepblue.inaction_00_java_nio.chapter_02.example_007;

import java.nio.CharBuffer;

/**
 *
 */
public class BatchExample01 {

    public static void main(String[] args) {

        CharBuffer buffer = CharBuffer.allocate(100);

        buffer.put("welcome to the java nio");

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        buffer.flip();

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        while(buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

        buffer.clear();

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        buffer.put("welcome to java nio", 0, 1);

        buffer.flip();

        while(buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
