package com.app.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "message")
public class ElasticMessage {
    @Id
    private String id;

    private String messageFrom;

    private String messageTo;

    private String messageText;

    private String messageDate;

    private String messageTime;

    private String userAvatar;
}
