package com.app.chat.service;

import com.app.chat.model.ElasticMessage;
import com.app.chat.model.UserMessage;
import com.app.chat.model.WordFrequency;
import com.app.chat.repository.MessageRepository;
import com.app.chat.repository.elastic.EMessageRepository;
import com.app.chat.spark.WordCountJob;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    
    private final EMessageRepository eMessageRepository;

    private final WordCountJob wordCountJob;

    @Autowired
    public MessageService(MessageRepository messageRepository, EMessageRepository eMessageRepository, WordCountJob wordCountJob) {
        this.messageRepository = messageRepository;
        this.eMessageRepository = eMessageRepository;
        this.wordCountJob = wordCountJob;
    }

    public UserMessage saveMessage(UserMessage message) throws IOException {
        writeMessageToFile(message);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = simpleDateFormat.format(new Date());

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = simpleTimeFormat.format(new Date());

        message.setMessageDate(formattedDate);
        message.setMessageTime(formattedTime);

        ElasticMessage elasticMessage = new ElasticMessage();
        BeanUtils.copyProperties(message, elasticMessage);
        
        eMessageRepository.save(elasticMessage);
        return messageRepository.save(message);
    }

    private void writeMessageToFile(UserMessage message) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(message.getMessageFrom() + ".txt", true));
        writer.append(' ');
        writer.append(message.getMessageText());

        writer.close();
    }

    public List<UserMessage> getAllMessagesForTopic(String topic){

        return messageRepository.findAllByMessageTo(topic);
    }

    public List<UserMessage> getPrivateMessages(String topic){

        return messageRepository.findAllByMessageTo(topic);
    }

    public List<UserMessage> getUserMessages(String username){

        return messageRepository.findAllByMessageFrom(username);
    }

    public List<ElasticMessage> searchMessages(String query){

        return eMessageRepository.findByMessageText(query);
    }

    public List<WordFrequency> getStatistics(String username){

        List<WordFrequency> frequencyList = wordCountJob.run(username);
        frequencyList.sort(Comparator.comparing(WordFrequency::getCount).reversed());
        return frequencyList;
    }
}
