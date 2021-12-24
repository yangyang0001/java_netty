package com.deepblue.inaction_01_netty_inaction.chapter_01.example_003;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;


/**
 * Netty核心组件 ChannelFuture 和 ChannelFutureListener
 */
public class ChannelFutureExample {

    public static void main(String[] args) {

        Channel channel = null;

        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));

        future.addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    future.channel().writeAndFlush(buffer);
                } else {
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });




    }
}
