package com.deepblue.inaction_01_netty_inaction.chapter_04.example_003;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.nio.charset.Charset;

/**
 *
 */
public class ChannelExample001 {

    public static void main(String[] args) {
        Channel channel = null;
        ByteBuf buf = Unpooled.copiedBuffer("Hello!", Charset.defaultCharset());
        channel.writeAndFlush(buf).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    System.out.println("write successful");
                } else {
                    System.out.println("write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
