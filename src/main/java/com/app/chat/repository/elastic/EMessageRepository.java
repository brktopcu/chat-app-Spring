package com.app.chat.repository.elastic;

import com.app.chat.model.ElasticMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EMessageRepository extends ElasticsearchRepository<ElasticMessage, String> {

    List<ElasticMessage> findByMessageText(String query);
}
