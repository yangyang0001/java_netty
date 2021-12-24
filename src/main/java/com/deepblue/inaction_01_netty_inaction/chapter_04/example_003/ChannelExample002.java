package com.deepblue.inaction_01_netty_inaction.chapter_04.example_003;

import io.netty.channel.Channel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 */
public class ChannelExample002 {

    public static Executor executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Channel channel = null;

        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush("Hello!");
            }
        };

        executor.execute(writer);

        // do other something

        executor.execute(writer);
    }
}
