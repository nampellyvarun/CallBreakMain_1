/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;

/**
 *
 * @author srivarun
 */
public class ExitProtocol {
    public void exit(Table table){
        String string = GameEncoder.getInstance().buildExitString();
        SendMessage.getInstance().sendMessageToTablePlayers(string, table);
    }
}
