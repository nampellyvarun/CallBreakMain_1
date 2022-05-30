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
public class GenerateCards {
    
    
    
    public ArrayList createCards(){
        
        ArrayList<Card> cardList = new ArrayList<>();
    
        cardList.add(new Card(14,'s'));
        cardList.add(new Card(13,'s'));
        cardList.add(new Card(12,'s'));
        cardList.add(new Card(11,'s'));
        cardList.add(new Card(10,'s'));
        cardList.add(new Card(9,'s'));
        cardList.add(new Card(8,'s'));
        cardList.add(new Card(7,'s'));
        cardList.add(new Card(6,'s'));
        cardList.add(new Card(5,'s'));
        cardList.add(new Card(4,'s'));
        cardList.add(new Card(3,'s'));
        cardList.add(new Card(2,'s'));
    
        cardList.add(new Card(14,'h'));
        cardList.add(new Card(13,'h'));
        cardList.add(new Card(12,'h'));
        cardList.add(new Card(11,'h'));
        cardList.add(new Card(10,'h'));
        cardList.add(new Card(9,'h'));
        cardList.add(new Card(8,'h'));
        cardList.add(new Card(7,'h'));
        cardList.add(new Card(6,'h'));
        cardList.add(new Card(5,'h'));
        cardList.add(new Card(4,'h'));
        cardList.add(new Card(3,'h'));
        cardList.add(new Card(2,'h'));
        
        cardList.add(new Card(14,'d'));
        cardList.add(new Card(13,'d'));
        cardList.add(new Card(12,'d'));
        cardList.add(new Card(11,'d'));
        cardList.add(new Card(10,'d'));
        cardList.add(new Card(9,'d'));
        cardList.add(new Card(8,'d'));
        cardList.add(new Card(7,'d'));
        cardList.add(new Card(6,'d'));
        cardList.add(new Card(5,'d'));
        cardList.add(new Card(4,'d'));
        cardList.add(new Card(3,'d'));
        cardList.add(new Card(2,'d'));
        
        cardList.add(new Card(14,'c'));
        cardList.add(new Card(13,'c'));
        cardList.add(new Card(12,'c'));
        cardList.add(new Card(11,'c'));
        cardList.add(new Card(10,'c'));
        cardList.add(new Card(9,'c'));
        cardList.add(new Card(8,'c'));
        cardList.add(new Card(7,'c'));
        cardList.add(new Card(6,'c'));
        cardList.add(new Card(5,'c'));
        cardList.add(new Card(4,'c'));
        cardList.add(new Card(3,'c'));
        cardList.add(new Card(2,'c'));
        
        return cardList;
    } 
    
    public ArrayList generateCardNumberList(){
        ArrayList<Integer> numberList = new ArrayList<>();
        for(int i=0;i<52;i++){
            numberList.add(i);
        }
        return numberList;
    }
    
    public ArrayList<Integer> cardNumberList = generateCardNumberList();
    
    public ArrayList<Card> cardList = createCards();
    
}
