package com.deepblue.inaction_00_java_nio.chapter_02.example_007;

import java.nio.CharBuffer;
import java.util.stream.Stream;

/**
 * 批量读取和写入
 */
public class BatchExample02 {

    public static void main(String[] args) {

        String source = "welcome to the java nio";

        CharBuffer buffer = CharBuffer.allocate(10);

        buffer.put(source, 0, buffer.capacity());

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        buffer.flip();

        char[] target = new char[buffer.capacity()];

        buffer.get(target);

        for (int i = 0; i < target.length; i++) {
            System.out.println(target[i]);
        }

    }
}
