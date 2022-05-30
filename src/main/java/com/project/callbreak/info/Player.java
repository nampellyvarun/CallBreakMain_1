/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.callbreak.info;

import com.project.callbreak.nio.api.NioConnectionInt;

/**
 *
 * @author srivarun
 */
public class Player {
    private String userId;
    private String status;
    private NioConnectionInt nioConnectionInt;
    private String tableId;
    
    

    public Player(String userId, String status, NioConnectionInt nioConnectionInt) {
        this.userId = userId;
        this.status = status;
        this.nioConnectionInt = nioConnectionInt;
    }

    public Player() {
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NioConnectionInt getNci() {
        return nioConnectionInt;
    }

    public void setNci(NioConnectionInt nioConnectionInt) {
        this.nioConnectionInt = nioConnectionInt;
    }

    @Override
    public String toString() {
        return "Player{" + "userId=" + userId + ", status=" + status + ", nioConnectionInt=" + nioConnectionInt + '}';
    }
    
    
}
