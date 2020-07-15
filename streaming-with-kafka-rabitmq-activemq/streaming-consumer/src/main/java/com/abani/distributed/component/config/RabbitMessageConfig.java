package com.abani.distributed.component.config;

import com.abani.distributed.component.component.RabbitMQListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMessageConfig {


    @Value("${spring.rabbitmq.queueName}")
    private String queueName;

    @Bean
    public Queue queue(){
        return new Queue(queueName, false);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                          MessageListenerAdapter listenerAdapter) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(rabbitMessageConverter());
        return containerFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter rabbitMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageListenerAdapter rabbitListenerAdapter(RabbitMQListener listener){
        return new MessageListenerAdapter(listener);
    }
}
