package com.project.callbreak.server.impl;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.project.callbreak.nio.CBInitializer;
import io.netty.util.HashedWheelTimerCust;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
//import com.project.callbreak.nio.CBProtocolExecutor;
//import io.netty.channel.ChannelHandlerContext;
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.InputStreamReader;
//import java.net.ServerSocket;
//import java.net.Socket;


/**
 * @author Amar Pratap, Yellaiah This class takes care initialization of lobby
 * server and make available to accept connection on specified port number. Also
 * initialize intercommunication(RMI registry)
 */
public class CBServer extends Server{

    private static CBServer cbServer = null;

    public ConcurrentMap<String, Integer> connectedSet = new ConcurrentHashMap<String, Integer>();

    public ConcurrentMap<String, Integer> getConnectedSet() {
        return connectedSet;
    }

    public void setConnectedSet(ConcurrentMap<String, Integer> connectedSet) {
        this.connectedSet = connectedSet;
    }

    public static CBServer getInstance() {
        if (null == cbServer) {
            cbServer = new CBServer();
        }
        return cbServer;
    }
    
    




    public String serverResponse()throws Exception{
        return null;    
    }


    @Override
    public void start(String args[]) {
        try {
            /* 
             * Initialise the server with server master details from the
             * database
             */
            //System.out.println("lobby webscoket started- yellaiah1");
            this.initialize();
            System.out.println("lobby webscoket started- CallBreakMain1");
            AppContext.getInstance().loadMapHandlers();
            System.out.println("Handler loaded"+AppContext.getInstance().getMapHandler().toString());
            
            LobbyWebSocketServer lobbywebSocket = new LobbyWebSocketServer(getPortNumber() + 1000, getServerName());
            new Thread(lobbywebSocket, getServerName() + "WebSocketServer").start();
            //starting the Lobbywebsocket server
            //System.out.println("lobby webscoket started- yellaiah2");
            HashedWheelTimerCust timer = new HashedWheelTimerCust();
            super.createListner(new CBInitializer(timer));
            
            
            

        } catch (Exception ex) {
            System.out.println("Error while staring the server" + ex);
        }
    }

    @Override
    protected void initialize() throws Exception {

        try {
            super.initialize();
            /*
            Issue occured date :- 03 May 2018
            Issue :- Lobby server not getting started.
            Resolution :- Added 1 second sleep between these threads as we are facing issues with Loggers creation
            if both the threads are executed simultaneoulsy and Lobby server is getting hanged because of this.
            Also whenever a new thread is added add sleep accordingly if two threads are simulataneous.
             */
            System.out.println("LOBBY SERVER INITIALIZE - end");
        } catch (Exception e) {
            System.out.println("Exception at rmi " + e);
        }
    }

}