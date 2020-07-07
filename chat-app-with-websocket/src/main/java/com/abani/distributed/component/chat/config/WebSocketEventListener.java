package com.abani.distributed.component.chat.config;

import com.abani.distributed.component.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations sendingHelper;

    @EventListener
    public void handleNewConnectionListener(SessionConnectedEvent event){
        System.out.println("New user joined!");
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {

            System.out.println(username + " Left!");
            Message message = new Message();
            message.setSender(username);
            message.setType("LEAVE");

            sendingHelper.convertAndSend("/topic/group", message);
        }
    }
}
