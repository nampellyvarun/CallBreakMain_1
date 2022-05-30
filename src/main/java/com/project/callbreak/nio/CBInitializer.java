package com.project.callbreak.nio;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandlerExt;
import io.netty.util.HashedWheelTimerCust;

/**
 *
 * @author  
 */
public class CBInitializer extends ChannelInitializer<SocketChannel> {

    private final HashedWheelTimerCust timer;

    public CBInitializer(HashedWheelTimerCust timer) {
        this.timer = timer;

    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeLine = ch.pipeline();
        pipeLine.addLast("timeoutS", new ReadTimeoutHandlerExt(timer, 30));
        pipeLine.addLast("handler", new CBNioHandler());

    }
}