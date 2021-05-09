package com.app.chat.repository;

import com.app.chat.model.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<UserMessage, UUID> {

    List<UserMessage> findAllByMessageTo(String messageTo);
}
