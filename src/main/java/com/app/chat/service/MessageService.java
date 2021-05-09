package com.app.chat.service;

import com.app.chat.model.UserMessage;
import com.app.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public UserMessage saveMessage(UserMessage message){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = simpleDateFormat.format(new Date());

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = simpleTimeFormat.format(new Date());

        message.setMessageDate(formattedDate);
        message.setMessageTime(formattedTime);

        return messageRepository.save(message);
    }

    public List<UserMessage> getAllMessagesForTopic(String topic){

        return messageRepository.findAllByMessageTo(topic);
    }
}
