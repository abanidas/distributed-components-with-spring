package com.abani.distributed.component.controller;

import com.abani.distributed.component.model.MessageItem;
import com.abani.distributed.component.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/kafka")
public class WebController {

    private static final Logger LOG = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private KafkaProducerService producerService;

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaConsumer(@RequestBody MessageItem messageItem){
        messageItem.setUuid(UUID.randomUUID().toString());
        messageItem.setTime(LocalDateTime.now().toString());

        LOG.info("New message: '{}'", messageItem);
        producerService.send(messageItem);
    }
}