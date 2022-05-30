/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.handler;

import com.project.callbreak.info.Chair;
import com.project.callbreak.info.Player;
import com.project.callbreak.info.Table;
import com.project.callbreak.nio.HandlerInterface;
import com.project.callbreak.protocols.ActiveUsersProtocol;
import com.project.callbreak.protocols.BidAckProtocol;
import com.project.callbreak.server.impl.AppContext;
import com.project.callbreak.timer.DiscardTimer;
/**
 *
 * @author srivarun
 */
public class BidHandler implements HandlerInterface{
    

    @Override
    public String handle(String string, Player player) {
        System.out.println("BidHandler called");
        String userId = player.getUserId();
        Table table = AppContext.getInstance().getTableByTableId(player.getTableId());
        int bid =0;
        
        for (Chair chair : table.getChairs()) {
            if(chair.getGamePlayer().getPlayerId().equals(userId)){
                
                chair.getGamePlayer().setBid(Integer.parseInt(string));
                bid = chair.getGamePlayer().getBid();
            }
        }
        table.setCount(table.getCount()+1);
        BidAckProtocol bap = new BidAckProtocol();
        bap.bidAckProtocol(userId,table, bid);
        if(table.getCount()%4==0){
            
            ActiveUsersProtocol aup = new ActiveUsersProtocol();
            DiscardTimer dt = new DiscardTimer();
            dt.setActiveUserId(table.peekActiveUserId());
            aup.activeUsers(table, table.getActiveUserId());
            table.setDiscardTimer(dt);
            table.getDiscardTimer().start();
            table.getDiscardTimer().setActiveUsersCalled(false);
            
        }
        
        
        return null;
    }    
}
