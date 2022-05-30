/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.Card;
import com.project.callbreak.info.GenerateCards;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.server.impl.AppContext;
import com.project.callbreak.services.CutForSeat;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author srivarun
 */
public class CFSProtocol {
    public void cfsProtocol(Table table,GenerateCards gCards){
        CutForSeat cfs = new CutForSeat();
                
        ArrayList<Card> seating = cfs.CutForSeatLogic(gCards);

        String string = GameEncoder.getInstance().buildCFS(table, seating);
        
        SendMessage.getInstance().sendMessageToTablePlayers(string, table);
        
        System.out.println("CFS: "+string);
        table.setDistributorId(seating.get(0).getPlayerId());
        Collections.rotate(seating,-1);
        ArrayList<String> activeUsers=new ArrayList<>();
        for(Card card:seating)
            activeUsers.add(card.getPlayerId());    
        
        table.setActiveUsers(activeUsers);
        
        CDProtocol cdp = new  CDProtocol();
        cdp.cdProtocol(table,gCards.cardList);

    }
    
}