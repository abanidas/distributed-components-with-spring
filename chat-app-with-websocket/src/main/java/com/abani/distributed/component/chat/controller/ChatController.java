package com.abani.distributed.component.chat.controller;

import com.abani.distributed.component.chat.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/join")
    @SendTo("/topic/group")
    public Message join(@Payload Message message, SimpMessageHeaderAccessor messageHeaderAccessor){

        messageHeaderAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

    @MessageMapping("/send")
    @SendTo("/topic/group")
    public Message send(@Payload Message message){
        return message;
    }
}
