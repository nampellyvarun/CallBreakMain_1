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
import com.project.callbreak.info.Table;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.services.TrickWinner;
import java.util.ArrayList;
import java.util.Collections;

public class TrickWinnerProtocol
{ 
    public void trickWinnerCalculator(Table table)
    {
        TrickWinner trickWinner = new TrickWinner();
        String winnerId = trickWinner.trickWinnerCalculation(table.getTrickList());      
        String string = GameEncoder.getInstance().buildTrickWinner(table,winnerId);
        SendMessage.getInstance().sendMessageToTablePlayers(string, table);  
        System.out.println("Trick Winner: "+string);
        
        ArrayList<String> activeUsersList= table.getActiveUsersList();
        int i=0;
        while(!(activeUsersList.get(i).equals(winnerId)))
        {
            i++;
        }
        Collections.rotate(activeUsersList, 4-i);
        table.setActiveUsers(activeUsersList);

     
    }
}