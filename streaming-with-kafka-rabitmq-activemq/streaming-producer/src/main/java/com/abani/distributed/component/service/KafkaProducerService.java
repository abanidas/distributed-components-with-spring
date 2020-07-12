package com.abani.distributed.component.service;

import com.abani.distributed.component.model.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${spring.kafka.producer.topic.message}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, MessageItem> kafkaTemplate;

    public void send(MessageItem messageItem){
        LOG.info("Sent By Kafka");
        kafkaTemplate.send(topic, messageItem);
    }
}
