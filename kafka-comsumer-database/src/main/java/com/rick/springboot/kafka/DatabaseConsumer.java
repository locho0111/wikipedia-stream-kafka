package com.rick.springboot.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.rick.springboot.entity.WikimediaEntity;
import com.rick.springboot.repository.WikimediaDataRepository;

@Service
public class DatabaseConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConsumer.class);

    private WikimediaDataRepository dataRepository;

    public DatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String eventMessage) {
        LOG.info(String.format("Message received -> %s", eventMessage));
        WikimediaEntity wikimediaEntity = new WikimediaEntity();
        wikimediaEntity.setWikiEventData(eventMessage);

        dataRepository.save(wikimediaEntity);
    }
}
