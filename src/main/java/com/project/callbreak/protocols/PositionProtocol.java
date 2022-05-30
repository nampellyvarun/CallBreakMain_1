/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

/**
 *
 * @author srivarun
 */

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.Chair;
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import java.util.ArrayList;

public class PositionProtocol {
    
    public void playerPositions(Table table)
    {
        ArrayList<Chair> chairList = table.getChairs();
        Chair chair = chairList.get(chairList.size()-1);

        String string = GameEncoder.getInstance().buildPosition(chair);
        SendMessage.getInstance().send(string,chair.getPlayer());
        System.out.println("Player Position: "+string);
        
    }    
}