/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.thighRankt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.project.callbreak.services;

import com.project.callbreak.info.GenerateCards;
import com.project.callbreak.info.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author srivarun
 */
public class CutForSeat {


    public ArrayList<Card> CutForSeatLogic(GenerateCards gCards) {
        
        ArrayList<Card> alCFSCards = new ArrayList<>();

        
        ArrayList<Integer> cnl = gCards.cardNumberList;
        
        Collections.shuffle(cnl);




        
        //Generating 4 random cards and adding it to the HashSet
        for(int i=1;i<=4;i++){
            Card c = gCards.cardList.get(cnl.remove(0));
            c.setPlayerId(Integer.toString(i));
            alCFSCards.add(c);
            
        }
        
        
              
        Iterator<Card> itr1 = alCFSCards.iterator();
        Card cDistributor = itr1.next();
         Card cDiscarder = cDistributor;
                
        int highCard=cDistributor.getCardNumber();
        int lowCard=cDistributor.getCardNumber();
        char hSuit = cDistributor.getSuit();
        char lSuit=cDiscarder.getSuit();
        while(itr1.hasNext()){
            Card c = itr1.next();
            if(c.getCardNumber() > highCard){
                highCard = c.getCardNumber();
                cDistributor = c;
               
            }
            else if(c.getCardNumber() == highCard){
                  if((int)c.getSuit()>(int)hSuit){
                      cDistributor = c;
                  }
            }
            if(c.getCardNumber() < lowCard){
                lowCard = c.getCardNumber();
                cDiscarder = c;
            }
            else if(c.getCardNumber() == lowCard){
                  if((int)c.getSuit()<(int)lSuit){
                      cDiscarder = c;
                  }
            }
        }
         ArrayList<Card> alSeatArrangement = new ArrayList<>();
        alSeatArrangement.add(cDistributor);
        alSeatArrangement.add(cDiscarder);
        alCFSCards.remove(cDistributor);
        alCFSCards.remove(cDiscarder);
        
        
        Iterator<Card> itr2 = alCFSCards.iterator();
        Card c2 = itr2.next();
        
        lowCard=c2.getCardNumber();
        char suit = c2.getSuit();
        
        while(itr2.hasNext()){
            Card c = itr2.next();
            if(c.getCardNumber() < lowCard){
                lowCard = c.getCardNumber();
                c2 = c;
            }
            else if(c.getCardNumber() == lowCard){
                  if((int)c.getSuit()<(int)suit){
                      c2 = c;
                  }
            }
        }
        alSeatArrangement.add(c2);
        alCFSCards.remove(c2);
        alSeatArrangement.add(alCFSCards.remove(0));
        
 
        //Displaying the player who Distributes the cards and who Discards the cards

        StringBuilder sb = new StringBuilder();
        sb.append(alSeatArrangement.get(0).toString());
        for(int i=1;i<4;i++){
            sb.append(";").append(alSeatArrangement.get(i));
        }
        return alSeatArrangement;
    }
    

}