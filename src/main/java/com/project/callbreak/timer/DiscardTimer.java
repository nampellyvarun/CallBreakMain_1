/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.project.callbreak.timer; 

import com.project.callbreak.info.Player;
import com.project.callbreak.protocols.AutoDiscardProtocol;
import com.project.callbreak.server.impl.AppContext;

/**
 *
 * @author srivarun
 */
public class DiscardTimer extends Thread {

    
    private boolean run = true;
    private boolean activeUsersCalled;
    public String activeUserId;
    
    int counter = 0;

    public String getActiveUserId() {
        return activeUserId;
    }

    public void setActiveUserId(String activeUserId) {
        this.activeUserId = activeUserId;
    }
    

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
    
    public boolean isActiveUsersCalled() {
        return activeUsersCalled;
    }

    public void setActiveUsersCalled(boolean activeUsersCalled) {
        this.activeUsersCalled = activeUsersCalled;
    }
    
    

    
    @Override
    public void run() {
        try {
//            Thread.sleep(3000);
            System.out.println("Discard Timer Started");
            while(run){
                System.out.println("Discard Timer: "+ ++counter);
                if(isActiveUsersCalled()){
                    counter=0;
                    setActiveUsersCalled(false);
                }
                if(counter == 3){//10
                    AutoDiscardProtocol adp = new AutoDiscardProtocol();
                    Player player = AppContext.getInstance().getPlayerByUserId(getActiveUserId());
                    adp.autoDiscardProtocol(player);
                    counter=0;
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
}