package com.deepblue.inaction_00_java_nio.chapter_02.example_009;

import java.nio.CharBuffer;

/**
 *
 */
public class CopyExample {

    public static void main(String[] args) {

        String source = "welcome to the java nio";

        CharBuffer buffer = CharBuffer.allocate(10);

        buffer.put(source, 0, buffer.capacity());

        buffer.flip();

        CharBuffer buffer0 = buffer.duplicate();

        CharBuffer buffer1 = buffer.asReadOnlyBuffer();

        System.out.println("mark = " + buffer0.mark() + ", position = " + buffer0.position() + ", limit = " + buffer0.limit() + ", capacity = " + buffer0.capacity());

        System.out.println("mark = " + buffer1.mark() + ", position = " + buffer1.position() + ", limit = " + buffer1.limit() + ", capacity = " + buffer1.capacity());

        while(buffer0.hasRemaining()) {
            System.out.println("element = " + buffer0.get());
        }


    }
}
