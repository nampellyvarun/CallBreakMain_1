/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.info;

/**
 *
 * @author srivarun
 */

import com.project.callbreak.server.impl.AppContext;
import com.project.callbreak.timer.DiscardTimer;
import com.project.callbreak.timer.Disconnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Table {
    
    private ArrayList<Chair> chairList = new ArrayList<>();
    private String tableId;
    private String status = null;
    private ArrayList<Card> trickList = new ArrayList<>();
    private ArrayList<String> activeUsers = new ArrayList<>();
    private int round ;
    private int trickCount;
    private String distributorId;
    private DiscardTimer discardTimer = new DiscardTimer();
    private int count;
//    private String isTableA
    
    private Disconnection disconnection;

    public Disconnection getDisconnection() {
        return disconnection;
    }

    public void setDisconnection(Disconnection disconnection) {
        this.disconnection = disconnection;
    }
    
    
    public DiscardTimer getDiscardTimer() {
        return discardTimer;
    }

    public void setDiscardTimer(DiscardTimer discardTimer) {
        this.discardTimer = discardTimer;
    }

    
    public int getCount() {
        return count;
    }
    
    

    public void setCount(int count) {
        this.count = count;
    }
    
    


    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getActiveUserId() {
        String activeUserId = activeUsers.get(0);
        Collections.rotate(activeUsers,-1);
        return activeUserId;
    }
    
    public String peekActiveUserId(){
        return activeUsers.get(0); 
    }

    public ArrayList<String> getActiveUsersList() {
        return activeUsers;
    }
    
    
    public void setActiveUsers(ArrayList<String> activeUsers) {
        this.activeUsers = activeUsers;
    }
    

    public int getTrickCount() {
        return trickCount;
    }

    public void setTrickCount(int trickCount) {
        this.trickCount = trickCount;
    }
    
    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
    

    public ArrayList<Card> getTrickList() {
        return trickList;
    }

    public void addCardToTrickList(Card card) {
        if (trickList.size()==4){
            trickList.clear();
        }
        trickList.add(card);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addChair(GamePlayer gamePlayer)
    {
            Chair c = new Chair();
            c.setChairId();
            c.setGamePlayer(gamePlayer);
            Player player = AppContext.getInstance().getPlayerByUserId(gamePlayer.getPlayerId());
            c.setPlayer(player);
            chairList.add(c);
    }
    
    public ArrayList<Chair> getChairs(){
        return chairList;
    }

    public String getTableId() {
        return tableId;
    }
    
    public void setTableId() {
        
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 5;
            Random random = new Random();
            StringBuilder sb = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int) 
                  (random.nextFloat() * (rightLimit - leftLimit + 1));
                sb.append((char) randomLimitedInt);
            }
            this.tableId = sb.toString();
    }

    @Override
    public String toString() {
        return "Table{" + "chairList=" + chairList + ", tableId=" + tableId + ", status=" + status + '}';
    }
    
}