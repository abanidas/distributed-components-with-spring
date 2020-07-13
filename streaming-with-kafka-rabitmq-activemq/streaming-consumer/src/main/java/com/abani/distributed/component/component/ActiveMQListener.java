package com.abani.distributed.component.component;

import com.abani.distributed.component.model.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQListener {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQListener.class);

    @JmsListener(destination = "${spring.activemq.queueName}")
    public void receive(MessageItem message){
        LOG.info("ActiveMQ: Received message = {}", message);
    }
}
