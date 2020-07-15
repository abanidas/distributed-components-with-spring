package com.abani.distributed.component.controller;

import com.abani.distributed.component.model.MessageItem;
import com.abani.distributed.component.service.ActiveMQProducerService;
import com.abani.distributed.component.service.KafkaProducerService;
import com.abani.distributed.component.service.RabbitMQProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class WebController {

    private static final Logger LOG = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private RabbitMQProducerService rabbitMQProducerService;

    @Autowired
    private ActiveMQProducerService activeMQProducerService;

    @RequestMapping(value = "/publish_kafka", method = RequestMethod.POST)
    public String sendMessageToKafkaConsumer(@RequestParam String message){
        MessageItem messageItem = new MessageItem();
        messageItem.setMessage(message);
        messageItem.setUuid(UUID.randomUUID().toString());
        messageItem.setTime(LocalDateTime.now().toString());

        LOG.info("New message: '{}'", messageItem);
        kafkaProducerService.send(messageItem);
        return "redirect:kafka_producer.html";
    }

    @RequestMapping(value = "/publish_rabbit", method = RequestMethod.POST)
    public String sendMessageToRabbitMQConsumer(@RequestParam String message){
        MessageItem messageItem = new MessageItem();
        messageItem.setMessage(message);
        messageItem.setUuid(UUID.randomUUID().toString());
        messageItem.setTime(LocalDateTime.now().toString());

        LOG.info("New message: '{}'", messageItem);
        rabbitMQProducerService.send(messageItem);
        return "redirect:rabbitmq_producer.html";
    }

    @RequestMapping(value = "/publish_active", method = RequestMethod.POST)
    public String sendMessageToActiveMQConsumer(@RequestParam String message){
        MessageItem messageItem = new MessageItem();
        messageItem.setMessage(message);
        messageItem.setUuid(UUID.randomUUID().toString());
        messageItem.setTime(LocalDateTime.now().toString());

        LOG.info("New message: '{}'", messageItem);
        activeMQProducerService.send(messageItem);
        return "redirect:activemq_producer.html";
    }
}
