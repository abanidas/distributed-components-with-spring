package com.abani.distributed.component.component;

import com.abani.distributed.component.model.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQListener.class);

    @RabbitListener(queues = "${spring.rabbitmq.queueName}")
    public void receive(MessageItem message){
        LOG.info("RabbitMQ: Received message = {}", message);
    }
}
