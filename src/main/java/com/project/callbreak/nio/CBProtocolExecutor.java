/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.callbreak.nio;

import com.project.callbreak.info.Player;
import com.project.callbreak.nio.impl.NioConnection;
import com.project.callbreak.server.impl.AppContext;
import io.netty.channel.ChannelHandlerContext;
import java.util.StringTokenizer;


/**
 *
 * @author hithendra
 */
public class CBProtocolExecutor {
    
    private static CBProtocolExecutor instance = null;
    public static CBProtocolExecutor getInstance() {
        if (instance == null) {
            synchronized (Object.class) {
                instance = instance == null ? new CBProtocolExecutor() : instance;
            }
        }
        return instance;
    }

    public void executeProtocol(String buffer, ChannelHandlerContext ctx) {
        
        buffer = buffer.trim();
        if(!buffer.startsWith("ping")){
            System.out.println("protocol from client : "+buffer);  
        }
        StringTokenizer st = new StringTokenizer(buffer,"#");
        String protocol = st.nextToken();
        String details = st.nextToken();

        HandlerInterface handlerInterface = AppContext.getInstance().getMapHandler().get(protocol);
        
        
        String attachUserId = (String) ctx.attr(NIOConstants.ATTACHMENT).get();
        if (attachUserId == null){            
            if(buffer.startsWith("login#")){    
                
                StringTokenizer st1 = new StringTokenizer(details,",");
                String s= st1.nextToken();     
                ctx.attr(NIOConstants.ATTACHMENT).set(s);
                attachUserId=s;
                Player player = new Player();
                player.setUserId(s);
                NioConnection nioConnectionInt = new NioConnection();
                
                if (ctx != null) {
                    nioConnectionInt.setChannel(ctx.channel());
                    nioConnectionInt.setChannelContext(ctx);
                    nioConnectionInt.setProtocol(ctx.attr(NIOConstants.PROTOCOL).get());
                    player.setNci(nioConnectionInt);
                    AppContext.getInstance().addPlayer(s, player);
                    AppContext.getInstance().addPlayerChannel(ctx.channel(),s);
                }
                
            }
        }
        
        Player player = AppContext.getInstance().getPlayerByUserId(attachUserId);
        
        if(handlerInterface != null){
            handlerInterface.handle(details,player);
        }
        else{
            System.out.println("Handler not found");
        }
            
    }         
}

