/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.services;
import com.project.callbreak.info.Card;
import java.util.ArrayList;

/**
 *
 * @author srivarun
 */
public class TrickWinner {
        
        public String trickWinnerCalculation(ArrayList<Card> trickList){

            Card twc = trickList.get(0);
            
            for(int i=1;i<4;i++){
                Card c = trickList.get(i);
                if(c.getSuit()==twc.getSuit()){
                    if(c.getCardNumber() > twc.getCardNumber()){
                        twc=c;
                    }
                }
                else if(c.getSuit() == 's' && twc.getSuit()!='s'){
                    twc=c;
                }
            }
            return twc.getPlayerId();
        }
}
