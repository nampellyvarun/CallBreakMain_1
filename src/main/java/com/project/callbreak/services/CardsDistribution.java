/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.services;
import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.CreateCardsList;
import com.project.callbreak.info.Card;
import com.project.callbreak.info.Chair;
import com.project.callbreak.info.GamePlayer;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.server.impl.AppContext;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author srivarun
 */
public class CardsDistribution {
    
    private static CardsDistribution instance = null;
    public static CardsDistribution getInstance() {
        if (instance == null) {
            synchronized (Object.class) {
                instance = instance == null ? new CardsDistribution() : instance;
            }
        }
        return instance;
    }
        
    public CreateCardsList cardsDistributionLogic(ArrayList<Card> cardsList){
        
        
        ArrayList<Integer> allSpadeCards = new ArrayList<>();

        //Adding all Spade cards to allSpadeCards list
        for(int i=0;i<13;i++){
            allSpadeCards.add(i);
        }
        
        ArrayList<Integer> faceCards = new ArrayList<>();

        //Adding all Non-Spade Face Cards to faceCards list    
        
        faceCards.add(13);
        faceCards.add(14);
        faceCards.add(15);
        faceCards.add(16);
        faceCards.add(26);
        faceCards.add(27);
        faceCards.add(28);
        faceCards.add(29);
        faceCards.add(39);
        faceCards.add(40);
        faceCards.add(41);
        faceCards.add(42);

        

        
        ArrayList<Integer> nonSpadeNumberCards = new ArrayList<>();
        
        //Adding all Non-Spade Number Cards to nonSpadeNumberCards list                
        for(int i=17;i<26;i++){
            nonSpadeNumberCards.add(i);
        }
        for(int i=30;i<39;i++){
            nonSpadeNumberCards.add(i);
        }
        for(int i=43;i<52;i++){
            nonSpadeNumberCards.add(i);
        }
        
        CreateCardsList ccl = new CreateCardsList();
        
        ArrayList<Card> player1List = ccl.getPlayer1List();
        ArrayList<Card> player2List = ccl.getPlayer2List();
        ArrayList<Card> player3List = ccl.getPlayer3List();
        ArrayList<Card> player4List = ccl.getPlayer4List();
        

        //Shuffling the arrays using Collections
        Collections.shuffle(allSpadeCards);
        Collections.shuffle(nonSpadeNumberCards);
        
        //Assigning a spade card to each player
        Card c = cardsList.get(allSpadeCards.remove(0));
        player1List.add(c);
        c = cardsList.get(allSpadeCards.remove(0));
        player2List.add(c);
        c = cardsList.get(allSpadeCards.remove(0));
        player3List.add(c);
        c = cardsList.get(allSpadeCards.remove(0));
        player4List.add(c);
        

        ArrayList<Integer> temp = new ArrayList<>();
        
        temp.addAll(allSpadeCards);
        
        //Adding remaining Face Cards to faceCards list
        for(int i : temp ){
            if(  i == 0 || i== 1 || i== 2 || i== 3){
                allSpadeCards.remove(new Integer(i));
                faceCards.add(i);
            }
        }
        
        Collections.shuffle(faceCards);

//        Assigning a face card to each player if player doesn't have a face card
        int playerCardNumber =player1List.get(0).getCardNumber();
        if(playerCardNumber != 11 || playerCardNumber !=12 || playerCardNumber !=13 || playerCardNumber !=14){
            c = cardsList.get(faceCards.remove(0));
            player1List.add(c);
        }
        playerCardNumber=player2List.get(0).getCardNumber();
        if(playerCardNumber != 11 || playerCardNumber !=12 || playerCardNumber !=13 || playerCardNumber !=14){
            c = cardsList.get(faceCards.remove(0));
            player2List.add(c);
        }
        playerCardNumber=player3List.get(0).getCardNumber();
        if(playerCardNumber != 11 || playerCardNumber !=12 || playerCardNumber !=13 || playerCardNumber !=14){
            c = cardsList.get(faceCards.remove(0));
            player3List.add(c);
        }
        playerCardNumber=player4List.get(0).getCardNumber();
        if(playerCardNumber != 11 || playerCardNumber !=12 || playerCardNumber !=13 || playerCardNumber !=14){
            c = cardsList.get(faceCards.remove(0));
            player4List.add(c);
        }
        
        
        ArrayList<Integer> remainingCards = new ArrayList<>();

        //Adding remaining cards to remainingCards list        
        remainingCards.addAll(allSpadeCards);
        remainingCards.addAll(faceCards);
        remainingCards.addAll(nonSpadeNumberCards);

        //Shuffling the remainingCardsList using Collections        
        Collections.shuffle(remainingCards);

        while(!remainingCards.isEmpty()){
            if(player1List.size()!=13){
                c = cardsList.get(remainingCards.remove(0));
                player1List.add(c);
            }
            if(player2List.size()!=13){
                c = cardsList.get(remainingCards.remove(0));
                player2List.add(c);
            }
            if(player3List.size()!=13){
                c = cardsList.get(remainingCards.remove(0));
                player3List.add(c);
            }
            if(player4List.size()!=13){
                c = cardsList.get(remainingCards.remove(0));
                player4List.add(c);
            }
            
        }
        
        Collections.sort(player1List);
        Collections.sort(player2List);
        Collections.sort(player3List);
        Collections.sort(player4List);
        
        ccl.setPlayer1List(player1List);
        ccl.setPlayer2List(player2List);
        ccl.setPlayer3List(player3List);
        ccl.setPlayer4List(player4List);
        
        return ccl;    
    }
    
    public void playerCardsList(Table table, ArrayList<Card> cardsList){
                
        CreateCardsList ccl = CardsDistribution.getInstance().cardsDistributionLogic(cardsList);
                
        ArrayList<ArrayList<Card>> playerCardsList = ccl.playerList(ccl.getPlayer1List(), ccl.getPlayer2List(), ccl.getPlayer3List(), ccl.getPlayer4List());
        int i=0;
        
        ArrayList<Chair> chairList = table.getChairs();
        for(Chair chair : chairList){
            ArrayList<Card> list= new ArrayList<>(playerCardsList.get(i)); 
            GamePlayer gamePlayer = chair.getGamePlayer();
            for(Card card:list)
                card.setPlayerId(gamePlayer.getPlayerId());
            gamePlayer.setPlayerCards(list);
            chair.setGamePlayer(gamePlayer);
            i++;
            String string = GameEncoder.getInstance().buildPlayerCards(table,gamePlayer);
            SendMessage.getInstance().send(string,chair.getPlayer());
            System.out.println("Player "+chair.getGamePlayer().getPlayerId()+ " Cards: "+chair.getGamePlayer().getPlayerCards());
        }
    }
}

