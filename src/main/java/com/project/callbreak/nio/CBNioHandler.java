package com.project.callbreak.nio;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.project.callbreak.info.Player;
import com.project.callbreak.server.impl.AppContext;
import com.project.callbreak.server.impl.CBServer;
import io.netty.channel.ChannelHandlerContext;
import java.util.StringTokenizer;

/**
 *
 *
 * @author Yellaiah
 */
public class CBNioHandler extends AbstractNIOHandler {

    @Override
    public void channelConnected(ChannelHandlerContext ctx) throws Exception {

        CBServer.getInstance().getClientGroup().add(ctx.channel());

    }

    @Override
    protected void unmergeProtocols(String buffer, ChannelHandlerContext ctx) {
        StringTokenizer recievedProtocols = new StringTokenizer(buffer, "\n");
        while (recievedProtocols.hasMoreElements()) {
            CBProtocolExecutor.getInstance().executeProtocol(recievedProtocols.nextToken(), ctx);
        }

    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, Object atatchment) throws Exception {
        try {
            System.out.println("ChannelDisconnected-channelDisconnected: "+ ctx.channel());
            System.out.println("ChannelDisconnected-channelDisconnected: "+ ctx.channel().toString());
            String playerId = AppContext.getInstance().getPlayerIdFromChannelCollection(ctx.channel());
            Player player = AppContext.getInstance().getPlayerByUserId(playerId);
            player.setStatus("111");
            player.setNci(null);
            ctx.channel().close();
        } catch (Exception e) {
        }
        CBServer.getInstance().getClientGroup().remove(ctx.channel());
    }
}