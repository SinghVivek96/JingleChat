package com.jingle.JingleChat.Controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jingle.JingleChat.Entities.ChatMessage;

@Controller
public class WebSocketController {
 
 
 
    @MessageMapping("/chat.sendMessage/{chatTopic}")
    @SendTo("/topic/{chatTopic}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
 
    @MessageMapping("/chat.addUser/{chatTopic}")
    @SendTo("/topic/{chatTopic}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor,@DestinationVariable String chatTopic) {
        // Add username in web socket session
    	System.out.println("path variable is "+chatTopic);
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("chatTopic", chatTopic);
        return chatMessage;
    }
 
}