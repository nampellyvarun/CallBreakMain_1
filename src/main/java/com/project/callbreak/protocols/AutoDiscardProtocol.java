/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

import com.project.callbreak.gameencoders.GameEncoder;
import com.project.callbreak.info.Player;
import com.project.callbreak.messagesender.SendMessage;
import com.project.callbreak.services.AutoDiscard;

/**
 *
 * @author srivarun
 */
public class AutoDiscardProtocol {
    public void autoDiscardProtocol(Player player){
        
        AutoDiscard ad = new AutoDiscard();
        String discardedCard = ad.autoDiscard(player);
        String string = GameEncoder.getInstance().buildAutoDiscard(discardedCard);
        SendMessage.getInstance().send(string, player);
    }
    
}
