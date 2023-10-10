package web.loger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/logWebSocket")
public class LogWebSocket {
    private static Session session;

    @OnOpen
    public void onOpen(Session session) {
        LogWebSocket.session = session;
    }

    public static void sendLogMessage(String logMessage) {
        try {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(logMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
