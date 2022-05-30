/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.handler;

import com.project.callbreak.info.Card;
import com.project.callbreak.info.GamePlayer;
import com.project.callbreak.info.Player;
import com.project.callbreak.info.Table;
import com.project.callbreak.nio.HandlerInterface;
import com.project.callbreak.protocols.ActiveUsersProtocol;
import com.project.callbreak.protocols.TrickProtocol;
import com.project.callbreak.server.impl.AppContext;
import java.util.StringTokenizer;

/**
 *
 * @author srivarun
 */
public class TrickCardHandler implements HandlerInterface{

    @Override
    public String handle(String string, Player player) {
        StringTokenizer stringToknizer = new StringTokenizer(string,",");
        String tableId = player.getTableId();
        Table table = AppContext.getInstance().getTableByTableId(tableId);
        table.getDiscardTimer().setActiveUsersCalled(true);
        System.out.println("TrickCardHandler called");
        
        Card card = new Card(player.getUserId(),stringToknizer.nextToken().charAt(0),Integer.parseInt(stringToknizer.nextToken()));
        table.addCardToTrickList(card);
        GamePlayer gp = AppContext.getInstance().getGamePlayerByUserId(player.getUserId(), table);
        gp.getPlayerCards().remove(card);
        
        TrickProtocol trickProtocol = new TrickProtocol();
        trickProtocol.trickCardProtocol(player.getUserId(),table, card);
        
        if(table.getTrickCount()!=13){ //13
            table.getDiscardTimer().setActiveUserId(table.peekActiveUserId());
            ActiveUsersProtocol aup = new ActiveUsersProtocol();
            aup.activeUsers(table, table.getActiveUserId());
        }
        else{
            table.setTrickCount(0);
        }
        
        return null;
    }   
}