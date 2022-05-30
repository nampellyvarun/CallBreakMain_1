/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.GamePlayer;
import com.project.callbreak.info.Player;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.server.impl.AppContext;

/**
 *
 * @author srivarun
 */
public class CardsProtocol {
    public void cardsProtocol(Table table, GamePlayer gamePlayer) {
        
        String string = GameEncoder.getInstance().buildRemainingPlayerCards(table, gamePlayer);
        Player player = AppContext.getInstance().getPlayerByUserId(gamePlayer.getPlayerId());
        SendMessage.getInstance().send(string, player);
        System.out.println("Player remaining cards: "+string);
    }
}
