package org.example.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Date: 2016/11/28
 * Time: 23:16
 *
 * @author: Zero
 */
@ServerEndpoint("/chat_app")
public class JSRChatWebSocketEndpoint {
                              //Endpoint
    @OnMessage
    public void message(String message, Session session) {
        for (Session s : session.getOpenSessions()) {
            s.getAsyncRemote().sendText(message);
        }
    }

    @OnError
    public void err(Session session, Throwable t) {
        t.printStackTrace();
    }

    @OnOpen
    public void open(Session session, EndpointConfig config) throws IOException {
        session.getBasicRemote().sendText("Server Session Opened!");
        System.out.printf("session");
    }
}
