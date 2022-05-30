/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.gameencoders;

import com.project.callbreak.info.Card;
import com.project.callbreak.info.Chair;
import com.project.callbreak.info.GamePlayer;
import com.project.callbreak.info.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




/**
 *
 * @author srivarun
 */
public class GameEncoder {
    private static GameEncoder instance = null;
    public static GameEncoder getInstance() {
        if (instance == null) {
            synchronized (Object.class) {
                instance = instance == null ? new GameEncoder() : instance;
            }
        }
        return instance;
    }
    
    static int playerPos=0;
    
    public String buildLoginAck(String status){
        
        return "loginA#"+status;
        
    }
    
    public String buildAutoDiscard(String string){
        return "autodiscard#"+string;
    }
    
    public String buildPing(){
        return "ping#protocol";
    }
    public String buildPlayerCards(Table table, GamePlayer gamePlayer){
        
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Card> iter = gamePlayer.getPlayerCards().iterator();
        Card c = iter.next();
        stringBuilder.append(table.getTableId()).append(",").append(table.getRound()+1).append(",").append(c.getSuit()).append("@").append(c.getCardNumber());
        if(gamePlayer.getPlayerCards().size()>1){
            while(iter.hasNext()){

                c = iter.next();
                stringBuilder.append(":").append(c.getSuit()).append("@").append(c.getCardNumber());

            }
        }
        stringBuilder.append(",").append(gamePlayer.getPlayerId()).append(",").append(gamePlayer.getBid()).append(",").append(gamePlayer.getTricksWon()).append(",");
        stringBuilder.append(gamePlayer.getRoundScore()).append(",").append(gamePlayer.getTotalScore());
        return "cd#"+stringBuilder.toString();
    }
    
    public String buildRemainingPlayerCards(Table table, GamePlayer gamePlayer){
        
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Card> iter = gamePlayer.getPlayerCards().iterator();
        Card c = iter.next();
        stringBuilder.append(table.getTableId()).append(",").append(table.getRound()+1).append(",").append(c.getSuit()).append("@").append(c.getCardNumber());
        if(gamePlayer.getPlayerCards().size()>1){
            while(iter.hasNext()){

                c = iter.next();
                stringBuilder.append(":").append(c.getSuit()).append("@").append(c.getCardNumber());

            }
            
        }
        stringBuilder.append(",").append(gamePlayer.getPlayerId());
        return "cards#"+stringBuilder.toString();
    }
    
    public String buildCFS(Table table,ArrayList<Card> s){
        
        StringBuilder stringBuilder = new StringBuilder();
        int j=0;
        ArrayList<String> playerIdList = new ArrayList<>();
        ArrayList<Chair> chairList = table.getChairs();
        for (Chair chair : chairList) {
            playerIdList.add(chair.getPlayer().getUserId());
            
        }
        Collections.shuffle(playerIdList);
        for(String playerId : playerIdList){
              s.get(j).setPlayerId(playerId);
              stringBuilder.append(playerId).append(":").append(s.get(j).getSuit()).append(":").append(s.get(j).getCardNumber()).append(":").append(++j).append(",");
        }
        System.out.println("CFS: "+s);
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return "cfs#"+stringBuilder.toString();
    }
    
    
    public String buildActiveUser(){
        return "activeUser#currentUser";
    }
    
    
    
    public String buildPosition(Chair chair){
        
        StringBuilder stringBuilder = new StringBuilder();
        GamePlayer gamePlayer = chair.getGamePlayer();
        String playerId = gamePlayer.getPlayerId();
        stringBuilder.append(playerId).append(",").append(++playerPos);
        if(playerPos==4){
            playerPos=0;
        }
        return "position#"+stringBuilder.toString();
        
    }
    
    public String buildTrickWinner(Table table,String winnerId){
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(winnerId).append("@");
        ArrayList<Chair> chairList = table.getChairs();
        for( Chair chair : chairList)
        {
            GamePlayer gamePlayer = chair.getGamePlayer();
            if(winnerId.equals(gamePlayer.getPlayerId()))
            {
                int tricksWon = gamePlayer.getTricksWon();
                gamePlayer.setTricksWon(tricksWon+1);
            }
            stringBuilder.append(gamePlayer.getPlayerId()).append(":").append(gamePlayer.getTricksWon()).append(",");    

        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return "trickWinner#" + stringBuilder.toString();
        
    }
    
    public String buildScoreCard(Table table){
        
        ArrayList<Chair> chairList = table.getChairs();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(table.getTableId()).append("@");
        for(Chair chair : chairList){
            GamePlayer gamePlayer = chair.getGamePlayer();
            stringBuilder.append(gamePlayer.getPlayerId()).append(":").append(gamePlayer.getRoundScore()).append(":").append(gamePlayer.getTotalScore()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        
        return "scoreCard#"+stringBuilder.toString();
        
    } 
    
    public String buildCard(String tableId,Card card){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tableId).append(",").append(card.getPlayerId()).append(",").append(card.getSuit()).append(",").append(card.getCardNumber());
        return "trickCard#"+stringBuilder.toString();
    }
    
    public String buildBidAck(String userId,int bid){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userId).append(",").append(bid);
        return "bidA#"+stringBuilder.toString();
    }
    
    public String buildResult(Table table){
        StringBuilder result = new StringBuilder();
        ArrayList<Chair> chairList= table.getChairs();
        float arr[] = new float[4];
        HashMap<String,Float> ht = new HashMap<>();
        int i;
        for(i=0;i<4;i++){
            arr[i]=chairList.get(i).getGamePlayer().getTotalScore();
            ht.put(chairList.get(i).getGamePlayer().getPlayerId(),arr[i]);
        }
        
        float newArray[]= Arrays.copyOfRange(arr,0,arr.length);
        float totArray[]=new float[newArray.length];
        Arrays.sort(newArray);
        int j=0,rank=1;
        for(i=newArray.length-1;i>=0;i--){
            totArray[j]=newArray[i];
               j++;
        }
        List<Map.Entry<String, Float> > list = new LinkedList< >(ht.entrySet());
        Collections.sort(list, (Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) -> (o1.getValue()).compareTo(o2.getValue()));

        HashMap<String, Float> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        Map<String, Float> hm1 = temp;
        int ranks[] = new int[totArray.length];             
        ranks[0]=rank;
        int k=2;
        for(i = 1; i<totArray.length; i++){
            if(totArray[i] != totArray[i-1]){
                rank=k;
                ranks[i]=k;
                k++;
            }
            else{
                ranks[i]=rank;  
                k++;
            }
        }
           
        result.append(table.getTableId());
        i=ranks.length-1;
        for (Map.Entry<String, Float> en : hm1.entrySet()) {
            result.append(",").append(en.getKey()).append(":").append(ranks[i--]);
        }
        return "result#"+result;

    }
    
    public String buildExitString(){
        return "exit#exit";
    }
    
}
