/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.timer;

import com.project.callbreak.info.Chair;
import com.project.callbreak.info.Player;
import com.project.callbreak.info.Table;
import com.project.callbreak.server.impl.AppContext;
import java.util.ArrayList;


/**
 *
 * @author srivarun
 */
public class Disconnection extends Thread{
//    public void disconnection() {
//        
//    }
    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    
    @Override
    public void run(){
        Table table = AppContext.getInstance().getTableByTableId(getTableId());
        ArrayList<Chair> chairList = table.getChairs();
        while(true){
            
            try {
//                System.out.println("Checking Disconnections");
                
                for(Chair chair : chairList ){
                    Player player  = chair.getPlayer();
                    
                    if(player != null && player.getStatus().equals("111")){
                        chair.getPlayer().setNci(null);
                        System.out.println("Player DisConnected");
                    }
                }
                
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
