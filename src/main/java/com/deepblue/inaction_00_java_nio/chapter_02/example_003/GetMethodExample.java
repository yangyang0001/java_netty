package com.deepblue.inaction_00_java_nio.chapter_02.example_003;

import java.nio.ByteBuffer;

/**
 * 使用 hasRemaining() 判定 当前 position 和 limit 之间还有多少个元素!
 */
public class GetMethodExample {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        for(int i = 0; i < 8; i++) {
            buffer.put((byte) (65 + i));
        }

        buffer.flip();

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        for (int i = 0; buffer.hasRemaining(); i++) {
            System.out.println("i = " + i + ", element = " + (char) buffer.get());
        }


    }
}
