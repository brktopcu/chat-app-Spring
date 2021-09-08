package com.app.chat.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID messageId;

    private String messageFrom;

    private String messageTo;

    private String messageText;

    private String messageDate;

    private String messageTime;

    private String userAvatar;

}
