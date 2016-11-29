package org.example.websocket;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Created with IntelliJ IDEA.
 * Date: 2016/11/28
 * Time: 23:16
 *
 * @author: Zero
 */
@ServerEndpoint("/chat_app")
public class JSRChatWebSocketEndpoint {

    @OnMessage
    public void message(String message, Session session) {
        for (Session s : session.getOpenSessions()) {
            s.getAsyncRemote().sendText(message);
        }
    }
}
