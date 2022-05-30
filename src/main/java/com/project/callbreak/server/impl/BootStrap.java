/**
 *
 */
package com.project.callbreak.server.impl;


/**
 * @author Yellaiah This is boot strap server for all servers 
 server. command for starting the lobby server is "java BootStrap CBServer
 -DSERVER_CONFIG_XML=d:/opt/serverconfig_lobby.xml" Starting the
 RealPool/playpool (real pool/play pool ) is " "java BootStrap PoolServer
 -DSERVER_CONFIG_XML=d:/opt/serverconfig_realpool.xml "
 *
 *
 */
public class BootStrap  {

    public static void main(String[] args){
        try {
            
            CBServer.getInstance().start(args);
            
        } catch (Exception e) {
            System.out.println(" *  command for starting the lobby server is 'java BootStrap LobbyServer -DSERVER_CONFIG_XML=d:/opt/serverconfig.xml' " + e);
        }
    }
}





