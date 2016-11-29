package org.example.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class TestWebsocketClient {
    private Session session;


    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        session.getBasicRemote().sendText("Client Session Opened!");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println(message);
//        session.getAsyncRemote().sendText("from client: "+message);
    }


    public static void main(String[] args) throws Exception {
        //http://www.programcreek.com/java-api-examples/index.php?api=javax.websocket.WebSocketContainer
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        Session session = container.connectToServer(TestWebsocketClient.class, new URI("http://localhost:8080/chat_app"));
        while (true) {
            Thread.sleep(3000);
            session.getAsyncRemote().sendText("000");
        }
    }
}