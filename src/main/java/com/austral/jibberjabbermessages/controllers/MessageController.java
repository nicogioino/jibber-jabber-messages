package com.austral.jibberjabbermessages.controllers;

import com.austral.jibberjabbermessages.dtos.ChatInfoDto;
import com.austral.jibberjabbermessages.dtos.CreateChatDto;
import com.austral.jibberjabbermessages.dtos.SendMessageDto;
import com.austral.jibberjabbermessages.models.Chat;
import com.austral.jibberjabbermessages.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @Autowired
    public MessageController(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @PostMapping("/new-chat")
    public void createChat(@RequestBody CreateChatDto newChatDto){
        messageService.createChat(newChatDto.getUser1(),newChatDto.getUser2());
    }

    @GetMapping("/get-chat-info/{user1}/{user2}")
    public ChatInfoDto getMessages(@PathVariable String user2, @PathVariable String user1){
        return messageService.getChat(user1,user2);
    }

    @GetMapping("/get-chats/{userId}")
    public List<ChatInfoDto> getChats(@PathVariable String userId) {
        return messageService.getChats(userId);
    }

    @MessageMapping("/chat/{to}/{id}")
    public void sendMessage(@DestinationVariable String to, @Payload final SendMessageDto chatMessage, @DestinationVariable String id) {
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, chatMessage);
        messageService.sendMessage(id, to, chatMessage);
    }


}
