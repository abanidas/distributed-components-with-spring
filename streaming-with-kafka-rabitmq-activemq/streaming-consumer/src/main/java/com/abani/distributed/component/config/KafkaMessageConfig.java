package com.abani.distributed.component.config;

import com.abani.distributed.component.model.MessageItem;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaMessageConfig {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageConfig.class);

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> messageConfig(){
        LOG.info("Setting Kafka Properties");
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }

    @Bean
    public ConsumerFactory<String, MessageItem> consumerFactory(){
        LOG.info("Creating Kafka ConsumerFactory");
        return new DefaultKafkaConsumerFactory<>(
                messageConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(MessageItem.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageItem> kafkaListenerContainerFactory(){
        LOG.info("Creating Kafka Listener ContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, MessageItem> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
