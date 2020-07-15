package com.abani.distributed.component.component;

import com.abani.distributed.component.model.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "${spring.rabbitmq.queueName}")
    public void receive(MessageItem message){
        LOG.info("RabbitMQ: Received message = {}", message);
        messagingTemplate.convertAndSend("/topic.socket.rabbit", message);
    }
}
