package com.abani.distributed.component.service;

import com.abani.distributed.component.model.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "${spring.kafka.consumer.topic.message}")
    public void receive(MessageItem messageItem){
        LOG.info("Received message = {}", messageItem);
    }
}
