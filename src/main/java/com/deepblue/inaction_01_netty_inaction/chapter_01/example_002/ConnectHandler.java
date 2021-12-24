package com.deepblue.inaction_01_netty_inaction.chapter_01.example_002;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;

/**
 * Netty 核心组件: 回调
 * Netty内部使用回调处理事件的方式, 一个事件触发必然有一个回调方法被调用!
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println("Client " + ctx.channel().remoteAddress() + " connected!");
    }
}
