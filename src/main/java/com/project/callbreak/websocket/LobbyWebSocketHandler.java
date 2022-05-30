package com.project.callbreak.websocket;

import com.project.callbreak.nio.CBProtocolExecutor;
import com.project.callbreak.server.impl.CBServer;
import io.netty.channel.ChannelHandlerContext;
import java.util.StringTokenizer;

/**
 * Handles handshakes and messages
 */
public class LobbyWebSocketHandler extends AbstractWebSocketHandler {

    @Override
    protected void unmergeProtocols(String message, ChannelHandlerContext ctx) {

        // Send the uppercase string back.
        StringTokenizer recievedProtocols = new StringTokenizer(message, "\n");
        while (recievedProtocols.hasMoreElements()) {
            CBProtocolExecutor.getInstance().executeProtocol(recievedProtocols.nextToken(), ctx);
        }
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx) throws Exception {
        System.out.println("webscoket ctx " + ctx.channel().remoteAddress());
        CBServer.getInstance().getClientGroup().add(ctx.channel());

    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, Object atatchment) throws Exception {
        try {
            
            ctx.channel().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}