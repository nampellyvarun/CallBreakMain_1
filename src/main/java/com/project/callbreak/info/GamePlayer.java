/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.info;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author srivarun
 */
public class GamePlayer {
    private String playerId;
    private ArrayList<Card> playerCards;
    private int tricksWon;
    private int bid;
    private float roundScore;
    private float totalScore;
    private HashMap<Integer,Float> scoreCard; 
    private int AutoDiscardCount;
    
    public GamePlayer(){
        
    }

    public GamePlayer(String playerId, ArrayList<Card> playerCards, int tricksWon, int bid, float roundScore,float totalScore) {
        this.playerId = playerId;
        this.playerCards = playerCards;
        this.tricksWon = tricksWon;
        this.bid = bid;
        this.roundScore = roundScore;
        this.totalScore= totalScore;
    }

    public GamePlayer(String playerId) {
        this.playerId = playerId;
    }

    public int getAutoDiscardCount() {
        return AutoDiscardCount;
    }

    public void setAutoDiscardCount(int AutoDiscardCount) {
        this.AutoDiscardCount = AutoDiscardCount;
    }
    
    

    public HashMap<Integer, Float> getScoreCard() {
        return scoreCard;
    }

    public float getRoundScoreFromScoreCard(int round){
        return scoreCard.get(round);
    }
    public void addRoundScoreToScoreCard(int round,float roundScore) {
        scoreCard.put(round, roundScore);
    }
    
    

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public int getTricksWon() {
        return tricksWon;
    }

    public void setTricksWon(int tricksWon) {
        this.tricksWon = tricksWon;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public float getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(float roundScore) {
        this.roundScore = roundScore;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }
    

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Card c : playerCards){
            stringBuilder.append(c.getCardNumber()).append("@").append(c.getSuit()).append("|");
        }
        return playerId+","+tricksWon+","+bid+","+roundScore+","+totalScore+","+stringBuilder.toString()+"\n";
    }



}
