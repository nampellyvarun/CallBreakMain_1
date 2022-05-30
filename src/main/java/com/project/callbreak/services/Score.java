/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.services;

import com.project.callbreak.info.GamePlayer;
import com.project.callbreak.info.Table;
import java.text.DecimalFormat;

/**
 *
 * @author srivarun
 */
public class Score {
    public void ScoreCalculation(Table table,GamePlayer gamePlayer){

        int bid = gamePlayer.getBid();
        int tricksWon = gamePlayer.getTricksWon();
        float roundScore = 0;
        float totalScore = gamePlayer.getTotalScore();
        
        if(bid == tricksWon){
            roundScore = (float)bid;
        }
        else if(bid < tricksWon){
            roundScore = (float) ((float)bid + (0.1*(tricksWon-bid)));  
        }
        else{
            roundScore = tricksWon-bid;
        }
        

        totalScore = totalScore + roundScore;
        gamePlayer.setRoundScore(roundScore);
        DecimalFormat form = new DecimalFormat("0.00");
        totalScore = Float.parseFloat(form.format(totalScore));
        gamePlayer.setTotalScore(totalScore);
    }
    
}
