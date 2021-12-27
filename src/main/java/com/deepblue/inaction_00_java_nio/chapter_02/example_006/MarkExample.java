package com.deepblue.inaction_00_java_nio.chapter_02.example_006;

import java.nio.CharBuffer;

/**
 *
 */
public class MarkExample {

    public static void main(String[] args) {

        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put('A');
        buffer.put('B');
        buffer.put('C');
        buffer.put('D');
        buffer.put('E');
        buffer.put('F');

        buffer.position(2).mark().position(4);

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        buffer.reset();

    }
}
