package com.abani.distributed.component.service;

import com.abani.distributed.component.model.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerService {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQProducerService.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.topic.exchangeName}")
    private String topicExchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    public void send(MessageItem message){
        LOG.info("Sent by RabbitMQ");
        rabbitTemplate.convertAndSend(topicExchange, routingKey, message);
    }

}
