/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.Card;
import com.project.callbreak.info.Chair;
import com.project.callbreak.info.GenerateCards;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author srivarun
 */
public class TrickProtocol {
    public void trickCardProtocol(String userId, Table table,Card card){
        String string = GameEncoder.getInstance().buildCard(table.getTableId(),card);
        SendMessage.getInstance().sendMessageToOtherPlayers(userId, string, table);
        System.out.println("Player trick card: "+string);
        if(table.getTrickList().size()==4){   //4
            
            TrickWinnerProtocol twp = new TrickWinnerProtocol();
            twp.trickWinnerCalculator(table);
            table.setTrickCount(table.getTrickCount()+1);
            
            if(table.getTrickCount()!=13){
                for(Chair chair: table.getChairs() ){
                    CardsProtocol cardsProtocol = new CardsProtocol();
                    cardsProtocol.cardsProtocol(table,chair.getGamePlayer());
                }
            }
            
            if(table.getTrickCount()==13){  //13
                RoundScoreProtocol rsp = new RoundScoreProtocol();
                rsp.roundScoreProtocol(table);
                
                table.setRound(table.getRound()+1);
                table.getDiscardTimer().setRun(false);

                
                if(table.getRound()==5){   //5
                    ResultProtocol rp = new ResultProtocol();
                    rp.resultProtocol(table);
                    
                    ExitProtocol exit = new ExitProtocol();
                    exit.exit(table);
                    
                }
                else{
                    GenerateCards gCards = new GenerateCards();
                    CDProtocol cdp = new  CDProtocol();
                    cdp.cdProtocol(table,gCards.cardList);
                }
                
                String disId = table.getDistributorId();
                ArrayList<String> activeUsersList = table.getActiveUsersList();
                
                while(true){
                    if(!(activeUsersList.get(0).equals(disId))){
                        Collections.rotate(activeUsersList, -1);
                    }
                    else{
                        break;
                    }
                }
                Collections.rotate(activeUsersList, -1);
                table.setDistributorId(table.getActiveUsersList().get(0));
                Collections.rotate(activeUsersList, -1);
                table.setActiveUsers(activeUsersList);
            }
        }
    }
}