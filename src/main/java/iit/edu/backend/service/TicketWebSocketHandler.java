package iit.edu.backend.service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class TicketWebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(new TextMessage("Received: " + message.getPayload()));
    }

    public void sendUpdate(WebSocketSession session, String update) throws IOException {
        session.sendMessage(new TextMessage(update));
    }
}
