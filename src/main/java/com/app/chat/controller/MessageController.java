package com.app.chat.controller;

import com.app.chat.model.UserMessage;
import com.app.chat.service.MessageService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate template;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate template) {
        this.messageService = messageService;
        this.template = template;
    }


    @MessageMapping("/user-all")
    public void send(UserMessage message) {
        UserMessage messageToSend = messageService.saveMessage(message);
        template.convertAndSend("/topic/"+message.getMessageTo(), messageToSend);
    }

    @GetMapping("/allMessages/{topic}")
    public ResponseEntity<List<UserMessage>> getAllMessages(@PathVariable String topic){
        var messageList = messageService.getAllMessagesForTopic(topic);

        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
}
