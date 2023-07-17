package com.project.chat.controller;

import com.project.chat.model.ChatMessage;
import com.project.chat.model.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller

public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }

    @MessageMapping("/addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());

        ChatMessage systemMessage = new ChatMessage();
        systemMessage.setSenderName("Chat Bot");
        systemMessage.setChatText(chatMessage.getSenderName() + " has joined the chat.");
        systemMessage.setStatus(MessageType.JOIN);

        // Send the system message to all connected clients
        messagingTemplate.convertAndSend("/topic/public", systemMessage);
    }
}
