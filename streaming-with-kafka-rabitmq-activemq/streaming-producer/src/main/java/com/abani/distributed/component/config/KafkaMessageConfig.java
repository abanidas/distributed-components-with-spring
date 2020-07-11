package com.abani.distributed.component.config;

import com.abani.distributed.component.model.MessageItem;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaMessageConfig {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageConfig.class);

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> messageConfig(){
        LOG.info("Setting Kafka Properties");
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return properties;
    }

    @Bean
    public ProducerFactory<String, MessageItem> producerFactory(){
        LOG.info("Creating Kafka ProducerFactory");
        return new DefaultKafkaProducerFactory<>(messageConfig());
    }

    @Bean
    public KafkaTemplate<String, MessageItem> kafkaTemplate(){
        LOG.info("Creating Kafka Template");
        return new KafkaTemplate<>(producerFactory());
    }
}
