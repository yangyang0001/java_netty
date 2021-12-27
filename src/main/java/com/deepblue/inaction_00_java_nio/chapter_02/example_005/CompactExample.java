package com.deepblue.inaction_00_java_nio.chapter_02.example_005;

import java.nio.CharBuffer;

/**
 * 压缩使用时无价值, 这里只提供了一个example 仅供学习使用!
 */
public class CompactExample {

    public static void main(String[] args) {

        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put('A');
        buffer.put('B');
        buffer.put('C');
        buffer.put('D');
        buffer.put('E');
        buffer.put('F');

        buffer.flip();

        buffer.get();
        buffer.get();

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        buffer.compact();

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        buffer.put('G');
        buffer.put('H');

        while(buffer.hasRemaining()) {
            System.out.println("position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity() + ", element = " + (char) buffer.get());
        }

    }
}
