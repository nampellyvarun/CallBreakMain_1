/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.info;

import java.util.Random;

/**
 *
 * @author srivarun
 */

public class Chair {
    private GamePlayer gamePlayer;
    private String chairId;
    private Player player;

    public Chair(){
        
    }
    
    public Chair(GamePlayer gamePlayer) {
        super();
        this.gamePlayer = gamePlayer;
    }
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
    public String getChairId() {
        return chairId;
    }
    public void setChairId() {
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
            this.chairId = sb.toString();
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Chair{" + "gamePlayer=" + gamePlayer + ", chairId=" + chairId + ", player=" + player + '}';
    }
    
    

}
