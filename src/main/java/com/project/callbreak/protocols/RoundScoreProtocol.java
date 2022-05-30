/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.Chair;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.services.Score;

/**
 *
 * @author srivarun
 */
public class RoundScoreProtocol {
    
    public void roundScoreProtocol(Table table){
        Score score = new Score();
        
        for(Chair chair : table.getChairs() ){
            score.ScoreCalculation(table,chair.getGamePlayer());   
            chair.getGamePlayer().setBid(0);
            chair.getGamePlayer().setTricksWon(0);
            
        }
        
        String string = GameEncoder.getInstance().buildScoreCard(table);
        
        SendMessage.getInstance().sendMessageToTablePlayers(string, table);
        System.out.println("Score card: "+string);
    }
    
}
