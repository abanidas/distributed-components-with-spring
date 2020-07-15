package com.abani.distributed.component.component;

import com.abani.distributed.component.model.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageListener.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.consumer.topic.message}")
    public void receive(MessageItem messageItem){
        LOG.info("Kafka: Received message = {}", messageItem);
        messagingTemplate.convertAndSend("/topic.socket.kafka", messageItem);
    }
}
