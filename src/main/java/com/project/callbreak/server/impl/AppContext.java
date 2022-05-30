/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.server.impl;

import com.project.callbreak.handler.BidHandler;
import com.project.callbreak.handler.LoginHandler;
import com.project.callbreak.handler.PingHandler;
import com.project.callbreak.handler.TrickCardHandler;
import com.project.callbreak.info.Chair;
import com.project.callbreak.info.GamePlayer;
import com.project.callbreak.info.Player;
import com.project.callbreak.info.Table;
import java.util.HashMap;
import com.project.callbreak.nio.HandlerInterface;
import io.netty.channel.Channel;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author srivarun
 */
public class AppContext {
    private static AppContext instance = null;
    public static AppContext getInstance() {
        if (instance == null) {
            synchronized (Object.class) {
                instance = instance == null ? new AppContext() : instance;
            }
        }
        return instance;
    }
    
    HashMap<Channel,String> playerChannelCollection = new HashMap<>();

    public String getPlayerIdFromChannelCollection(Channel channel) {
        return playerChannelCollection.get(channel);
    }

    public void addPlayerChannel(Channel channel,String playerId) {
        playerChannelCollection.put( channel,playerId);
    }
    
    
    
    public GamePlayer getGamePlayerByUserId(String userId,Table table){
            for(Chair chair:table.getChairs()){
                if(chair.getGamePlayer().getPlayerId().equals(userId)){
                    return chair.getGamePlayer();
                }
            }
            return null;
    }
    
    LinkedHashMap<String,Table> tableCollection = new LinkedHashMap<>();

    public LinkedHashMap<String, Table> getTableCollection() {
        return tableCollection;
    }

    public void setTableCollection(LinkedHashMap<String, Table> tableCollection) {
        this.tableCollection = tableCollection;
    }
    
    public Table getTableByTableId(String tableId){
        return tableCollection.get(tableId);
    }
    
    public void addTable(String tableId,Table table){
        tableCollection.put(tableId,table);
    }
    
    public Table getLatestTable(){
        Table table  = new Table();
        for (Map.Entry<String, Table> entry : tableCollection.entrySet()) {
                table  = entry.getValue();   
        }
        return table;
    }
    
    
    HashMap<String,Player> playerCollection = new HashMap<>();

    public HashMap<String, Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(HashMap<String, Player> playerCollection) {
        this.playerCollection = playerCollection;
    }

    public Player getPlayerByUserId(String userId) {
        return playerCollection.get(userId);
    }

    public void addPlayer(String userId,Player player) {
        playerCollection.put(userId, player);
    }
    
   HashMap<String,HandlerInterface> mapHandler = new HashMap<>(); 


    public HashMap<String, HandlerInterface> getMapHandler() {
        return mapHandler;
    }

    public void setMapHandler(HashMap<String, HandlerInterface> mapHandler) {
        this.mapHandler = mapHandler;
    }
    
    public void loadMapHandlers(){
        
        LoginHandler loginHandler = new LoginHandler();
        mapHandler.put("login",loginHandler);
        
        BidHandler bidHandler = new BidHandler();
        mapHandler.put("bid", bidHandler);
        
        TrickCardHandler trickCardHandler = new TrickCardHandler();
        mapHandler.put("trickcard",trickCardHandler);
        
        PingHandler pingHandler = new PingHandler();
        mapHandler.put("ping", pingHandler);
    }

}
