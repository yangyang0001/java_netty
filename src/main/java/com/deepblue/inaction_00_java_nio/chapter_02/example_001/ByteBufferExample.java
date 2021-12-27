package com.deepblue.inaction_00_java_nio.chapter_02.example_001;

import java.nio.ByteBuffer;


/**
 * 经验证 get(i) 时并不会出现 越界的危险, 但是要注意空的处理!
 */
public class ByteBufferExample {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);


        buffer.put((byte) 'H').put((byte)'e').put((byte)'l').put((byte)'l').put((byte)'o');

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());


        // 用 position 控制空元素的处理
        for(int i = 0; i < buffer.position(); i++) {
            System.out.println("i = " + i + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity() + ", element = " + (char) buffer.get(i));
        }

        System.out.println("------------------------------------------------------------------------------------------------");

        buffer.put(0, (byte) 'M').put((byte) 'w');

        System.out.println("mark = " + buffer.mark() + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity());

        // TODO 未用 position 进行空元素位置的判定
        for(int i = 0; i < buffer.capacity(); i++) {
            System.out.println("i = " + i + ", position = " + buffer.position() + ", limit = " + buffer.limit() + ", capacity = " + buffer.capacity() + ", element = " + (char) buffer.get(i));
        }


    }
}
