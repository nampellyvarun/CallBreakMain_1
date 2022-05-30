/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;
import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.server.impl.AppContext;

/**
 *
 * @author srivarun
 */
public class ResultProtocol {
    public void resultProtocol(Table table){
        
        String string= GameEncoder.getInstance().buildResult(table);
        SendMessage.getInstance().sendMessageToTablePlayers(string, table);
        AppContext.getInstance().getTableCollection().remove(table.getTableId());
        System.out.println("Result: "+string);
    }
    
}