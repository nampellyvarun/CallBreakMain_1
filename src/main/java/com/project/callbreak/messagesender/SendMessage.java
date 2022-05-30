/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.messagesender;

import com.project.callbreak.info.Chair;
import com.project.callbreak.info.Player;
import com.project.callbreak.info.Table;
import java.util.ArrayList;


/**
 *
 * @author srivarun
 */
public class SendMessage {
    private static SendMessage instance = null;
    public static SendMessage getInstance() {
        if (instance == null) {
            synchronized (Object.class) {
                instance = instance == null ? new SendMessage() : instance;
            }
        }
        return instance;
    }
    public void send(String string,Player player){
        
        player.getNci().sendMessage(string);
    }
    
    public void sendMessageToTablePlayers(String string,Table table){
        ArrayList<Chair> chairList = table.getChairs();
        for (Chair chair : chairList) {
            SendMessage.getInstance().send(string,chair.getPlayer());
        }
    }
    
    public void sendMessageToOtherPlayers(String userId,String string,Table table){
        ArrayList<Chair> chairList = table.getChairs();
        for (Chair chair : chairList) {
            if(!userId.equals(chair.getPlayer().getUserId())){
                SendMessage.getInstance().send(string,chair.getPlayer());
            }
        }
    }
}
