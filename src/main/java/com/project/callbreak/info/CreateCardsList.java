/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.info;


import java.util.ArrayList;

/**
 *
 * @author srivarun
 */
public class CreateCardsList {
    
    private ArrayList<Card> player1List = new ArrayList<>();
    private ArrayList<Card> player2List = new ArrayList<>();
    private ArrayList<Card> player3List = new ArrayList<>();
    private ArrayList<Card> player4List = new ArrayList<>();

    public CreateCardsList() {
    }
    
    public ArrayList<ArrayList<Card>> playerList(ArrayList<Card> player1List,ArrayList<Card> player2List,ArrayList<Card> player3List,ArrayList<Card> player4List){
        ArrayList<ArrayList<Card>> playerCardsList = new ArrayList<>();
        playerCardsList.add(player1List);
        playerCardsList.add(player2List);
        playerCardsList.add(player3List);
        playerCardsList.add(player4List);
        return playerCardsList;
    }

    public ArrayList<Card> getPlayer1List() {
        return player1List;
    }

    public void setPlayer1List(ArrayList<Card> player1List) {
        this.player1List = player1List;
    }

    public ArrayList<Card> getPlayer2List() {
        return player2List;
    }

    public void setPlayer2List(ArrayList<Card> player2List) {
        this.player2List = player2List;
    }

    public ArrayList<Card> getPlayer3List() {
        return player3List;
    }

    public void setPlayer3List(ArrayList<Card> player3List) {
        this.player3List = player3List;
    }

    public ArrayList<Card> getPlayer4List() {
        return player4List;
    }

    public void setPlayer4List(ArrayList<Card> player4List) {
        this.player4List = player4List;
    }
}
