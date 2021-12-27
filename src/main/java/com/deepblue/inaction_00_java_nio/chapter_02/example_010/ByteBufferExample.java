package com.deepblue.inaction_00_java_nio.chapter_02.example_010;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 */
public class ByteBufferExample {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.order().toString());
        System.out.println(ByteOrder.nativeOrder());
    }

}
