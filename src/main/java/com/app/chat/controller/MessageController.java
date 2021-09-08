package com.app.chat.controller;

import com.app.chat.model.ElasticMessage;
import com.app.chat.model.UserMessage;
import com.app.chat.model.WordFrequency;
import com.app.chat.service.MessageService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    public void send(UserMessage message) throws IOException {
        UserMessage messageToSend = messageService.saveMessage(message);
        template.convertAndSend("/topic/"+message.getMessageTo(), messageToSend);
    }

    @GetMapping("/allMessages/{topic}")
    public ResponseEntity<List<UserMessage>> getAllMessages(@PathVariable String topic){
        var messageList = messageService.getAllMessagesForTopic(topic);

        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @GetMapping("/privateMessages/{user}")
    public ResponseEntity<List<UserMessage>> getPrivateMessages(@PathVariable String user){
        var messageList = messageService.getPrivateMessages(user);

        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @GetMapping("/userMessages/{user}")
    public ResponseEntity<List<UserMessage>> getAllUserMessages(@PathVariable String user){
        var messageList = messageService.getUserMessages(user);

        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @GetMapping("/searchMessages/{query}")
    public ResponseEntity<List<ElasticMessage>> searchMessages(@PathVariable String query){
        var messageList = messageService.searchMessages(query);

        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @GetMapping("/statistics/{username}")
    public ResponseEntity<List<WordFrequency>> getStatistics(@PathVariable String username){
        var wordList = messageService.getStatistics(username);

        return new ResponseEntity<>(wordList, HttpStatus.OK);
    }
}
