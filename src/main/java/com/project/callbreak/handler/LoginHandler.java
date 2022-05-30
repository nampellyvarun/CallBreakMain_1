/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.handler;

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.*;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.nio.HandlerInterface;
import com.project.callbreak.protocols.PositionProtocol;
import com.project.callbreak.server.impl.AppContext;
import com.project.callbreak.timer.Disconnection;
import com.project.callbreak.timer.StartGameTimer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

/**
 *
 * @author srivarun
 */

public class LoginHandler implements HandlerInterface{
    
    public int userCount = 0;
    @Override
    public String handle(String string,Player player) {
        System.out.println("LoginHandler called");
        System.out.println("Login handler parameters "+string+" "+player.toString());
        string = string.trim();
        
        StringTokenizer stringTokenizer = new StringTokenizer(string,",");
        String userID = stringTokenizer.nextToken();
        String pwd = stringTokenizer.nextToken();
        FileReader fileReader = null;
        int x = 0;
        String stringStatus="";
        try {
            File file = new File("Details.txt");
            fileReader = new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line;
            try {
                while((line=bufferedReader.readLine())!=null)
                {
                    StringTokenizer st2 = new StringTokenizer(line,",");
                    String userid = st2.nextToken();
                    String pass = st2.nextToken();
                    
                    if(userID.equals(userid) && pwd.equals(pass)){
//                        Player p = AppContext.getInstance().getPlayerByUserId(userid);
//                        if(p.getNci()==null){
//                            Table t = AppContext.getInstance().getTableByTableId(p.getTableId());
//                            for(Chair c: t.getChairs()){
//                                if(c.getPlayer().getUserId().equals(userid)){
//                                    player.setStatus("100");
//                                    player.setTableId(p.getTableId());
////                                    player.setUserId(userid);
//                                    c.setPlayer(player);
//                                }
//                            }
//                            
//                            break;
//                        }
                        x=1;
                        userCount++;
                        player.setStatus("100");
                        stringStatus = GameEncoder.getInstance().buildLoginAck("100");
                        if(AppContext.getInstance().getTableCollection().isEmpty()){
                            LinkedHashMap<String,Table> tableCollection = new LinkedHashMap<>();
                            AppContext.getInstance().setTableCollection(tableCollection);
                            Table table = new Table();
                            table.setTableId();
                            String tableId=table.getTableId();
                            
                            GamePlayer gamePlayer = new GamePlayer(player.getUserId());
                            table.addChair(gamePlayer);
                            table.setStatus("registering");
                            AppContext.getInstance().addTable(tableId, table);
                            System.out.println("Table: 1st player joined the game: "+gamePlayer.getPlayerId());
 
                        }
                        else{
                            Table table  = AppContext.getInstance().getLatestTable();
                            if(table.getStatus().equals("registering")){
                                GamePlayer gamePlayer = new GamePlayer(player.getUserId());
                                table.addChair(gamePlayer);
                                System.out.println("Table: other players joining the game: "+gamePlayer.getPlayerId());
                                if(table.getChairs().size()==4){
                                    String tableId = table.getTableId();
                                    System.out.println("Table is full and ready to start");
                                    ArrayList<Chair> chairList = table.getChairs();
                                    for(Chair chair : chairList){
                                        chair.getPlayer().setTableId(tableId);
                                    }
                                    table.setStatus("full");
                                }
                            }
                            else{
                                Table table1 = new Table();
                                table1.setTableId();
                                String tableId = table1.getTableId();
                                GamePlayer gamePlayer = new GamePlayer(player.getUserId());
                                table1.addChair(gamePlayer);
                                table1.setStatus("registering");
                                
                                AppContext.getInstance().addTable(tableId, table1);
                            }
                        }    
                        break;            
                    }  
                }
            } catch (IOException ex) {
                System.out.println(ex);
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        String ping = GameEncoder.getInstance().buildPing();
        if(x==1){
            System.out.println("Valid");
            player.setStatus("100");
            System.out.println("Player : "+player.toString());
            stringStatus = GameEncoder.getInstance().buildLoginAck("100");
            player.setUserId(userID);
            System.out.println("Ak status: "+stringStatus); 
        }
        else{
            System.out.println("Invalid");
            player.setStatus("111");
            stringStatus = GameEncoder.getInstance().buildLoginAck("111");
            System.out.println("Ak status: "+stringStatus);
        }
        try {
            SendMessage.getInstance().send(stringStatus,player);
            SendMessage.getInstance().send(ping, player);
            Table table  = AppContext.getInstance().getLatestTable();
            PositionProtocol positionProtocol = new PositionProtocol();
            positionProtocol.playerPositions(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        stringStatus = "";
        
        if(AppContext.getInstance().getLatestTable().getChairs().size() ==4){
            Table table = AppContext.getInstance().getLatestTable();
            StartGameTimer startGameTimer = new StartGameTimer();
            String stringStart = "sTimer#5";
            SendMessage.getInstance().sendMessageToTablePlayers(stringStart, table);
            startGameTimer.start();
//            Disconnection d = new Disconnection();
//            d.setTableId(table.getTableId());
//            d.start();
            
        }
        return null;   
    }

   
}
    

