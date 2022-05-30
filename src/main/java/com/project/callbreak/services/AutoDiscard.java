/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.services;

import com.project.callbreak.handler.TrickCardHandler;
import com.project.callbreak.info.Card;
import com.project.callbreak.info.GamePlayer;
import com.project.callbreak.info.Player;
import com.project.callbreak.info.Table;
import com.project.callbreak.server.impl.AppContext;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author srivarun
 */
public class AutoDiscard {
    public String autoDiscard(Player player){
        TrickCardHandler trickCardHandler = new TrickCardHandler();
        
        Card card = null ;
        Random random = new Random();
        int randomInt =0;
        int range=0;
        
        Table table = AppContext.getInstance().getTableByTableId(player.getTableId());
        GamePlayer gamePlayer = AppContext.getInstance().getGamePlayerByUserId(player.getUserId(), table);
        ArrayList<Card> cardList = gamePlayer.getPlayerCards();
        int trickListSize = table.getTrickList().size();
        
        if(trickListSize!=0){
            Card prevCard = table.getTrickList().get(0);
            ArrayList<Card> validSet = new ArrayList<>();
            for(Card c : cardList){
                if(prevCard.getSuit()==c.getSuit()){
                    validSet.add(c);
                }
            }
            if(!validSet.isEmpty()){
                range = validSet.size()-1;
                randomInt = (int)(random.nextFloat() * (range + 1));
                card = validSet.get(randomInt);
            }
            else{
                for(Card c : cardList){
                    if(c.getSuit()=='s'){
                        validSet.add(c);
                    }
                }
                if(!validSet.isEmpty()){
                    range = validSet.size()-1;
                    randomInt = (int)(random.nextFloat() * (range + 1));
                    card = validSet.get(randomInt);
                }
                else{
                    range = cardList.size()-1;
                    randomInt = (int)(random.nextFloat() * (range + 1));
                    card = cardList.get(randomInt); 
                }
            }
        }
        else{
            range = cardList.size()-1;
            randomInt = (int)(random.nextFloat() * (range + 1));
            card = cardList.get(randomInt);  
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(card.getSuit()).append(",").append(card.getCardNumber());
        
        trickCardHandler.handle(stringBuilder.toString(), player);
        return stringBuilder.toString();
    }
}
