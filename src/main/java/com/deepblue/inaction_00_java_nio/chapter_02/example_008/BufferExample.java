package com.deepblue.inaction_00_java_nio.chapter_02.example_008;

import java.nio.*;

/**
 * 这七种 Buffer 都是间接内存
 */
public class BufferExample {

    public static void main(String[] args) {

        IntBuffer    buffer1 = IntBuffer.allocate(10);
        ShortBuffer  buffer2 = ShortBuffer.allocate(10);
        LongBuffer   buffer3 = LongBuffer.allocate(10);
        FloatBuffer  buffer4 = FloatBuffer.allocate(10);
        DoubleBuffer buffer5 = DoubleBuffer.allocate(10);
        ByteBuffer   buffer6 = ByteBuffer.allocate(10);
        CharBuffer   buffer7 = CharBuffer.allocate(10);

        System.out.println("buffer1 hasArray = " + buffer1.hasArray() + ", isDirect = " + buffer1.isDirect());
        System.out.println("buffer2 hasArray = " + buffer2.hasArray() + ", isDirect = " + buffer2.isDirect());
        System.out.println("buffer3 hasArray = " + buffer3.hasArray() + ", isDirect = " + buffer3.isDirect());
        System.out.println("buffer4 hasArray = " + buffer4.hasArray() + ", isDirect = " + buffer4.isDirect());
        System.out.println("buffer5 hasArray = " + buffer5.hasArray() + ", isDirect = " + buffer5.isDirect());
        System.out.println("buffer6 hasArray = " + buffer6.hasArray() + ", isDirect = " + buffer6.isDirect());
        System.out.println("buffer7 hasArray = " + buffer7.hasArray() + ", isDirect = " + buffer7.isDirect());

    }
}
