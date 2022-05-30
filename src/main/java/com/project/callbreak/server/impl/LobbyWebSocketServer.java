package com.project.callbreak.server.impl;

import com.project.callbreak.websocket.LobbyWebSocketInit;
import io.netty.util.HashedWheelTimerCust;

/**
 * A HTTP server which lobby web server extends websocket behaviour.
 *
 * http://IP:port/html
 *
 *
 * This server illustrates support for the different web socket specification
 * versions and will work with:
 *
 * <ul>
 * <li>Safari 5+ (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 6-13 (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 14+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Chrome 16+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * <li>Firefox 7+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Firefox 11+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * </ul>
 */
public class LobbyWebSocketServer extends WebSocketServer implements Runnable {

    public LobbyWebSocketServer(int port, String serverName) {
        setPort(port);
        setServerName(serverName);
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new LobbyWebSocketServer(port, "Lobby");

    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            //System.out.println("lobby websocket started");
            HashedWheelTimerCust timer = new HashedWheelTimerCust();
            start(new LobbyWebSocketInit(timer));
        } catch (Exception e) {
            System.out.println("Exception while starting lobby web socket server " + e);
        }
    }
}