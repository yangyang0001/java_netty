package com.deepblue.inaction_00_java_nio.chapter_02.example_002;

import java.nio.ByteBuffer;

/**
 * flip() 将 position 重置为0, 进行读取
 */
public class FlipExample {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        for(int i = 0; i < 6; i++) {
            buffer.put((byte) (65 + i));
        }


        // 读取位置翻转
        buffer.flip();

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        System.out.println("------------------------------------------------------------------------------------------------");

        for (int i = 0; buffer.hasRemaining() ; i++) {
            System.out.println("i = " + i + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity() + ", element = " + (char) buffer.get());
        }

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

    }
}
