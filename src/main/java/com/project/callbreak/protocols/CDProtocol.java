/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.protocols;

import com.project.callbreak.info.Card;
import com.project.callbreak.info.Table;
import com.project.callbreak.services.CardsDistribution;
import java.util.ArrayList;

/**
 *
 * @author srivarun
 */
public class CDProtocol {

    
    public void cdProtocol(Table table, ArrayList<Card> cardsList){
        CardsDistribution cardDistribution = new CardsDistribution();
        cardDistribution.playerCardsList(table,cardsList);
    }
}
