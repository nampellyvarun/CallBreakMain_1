/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.callbreak.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.ReadTimeoutHandlerExt;
import io.netty.util.HashedWheelTimerCust;

/**
 *
 * @author Yellaiah
 */
public class LobbyWebSocketInit extends ChannelInitializer<SocketChannel> {

    private HashedWheelTimerCust timer = null;

    public LobbyWebSocketInit(HashedWheelTimerCust timer) {
        this.timer = timer;
    }

    // private final Timer timer;

    /*  public LobbyPipelineFactory(Timer timer) {       
     this.timer = timer;

     }*/
    @Override
    public void initChannel(SocketChannel ch) throws Exception {

        try {
            ChannelPipeline pipeLine = ch.pipeline();//,

            pipeLine.addLast("codec-http", new HttpServerCodec());
            pipeLine.addLast("aggregator", new HttpObjectAggregator(65536));
            pipeLine.addLast("timeout", new ReadTimeoutHandlerExt(timer, 30));
            pipeLine.addLast("handler", new LobbyWebSocketHandler());

        } catch (Exception e) {
            System.out.println("Exception at LobbyWebSocketInit " + e);
        }

    }
}