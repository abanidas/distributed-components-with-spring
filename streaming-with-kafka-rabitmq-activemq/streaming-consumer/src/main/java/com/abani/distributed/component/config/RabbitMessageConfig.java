package com.abani.distributed.component.config;

import com.abani.distributed.component.component.RabbitMQListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMessageConfig {

    @Value("${spring.rabbitmq.topic.exchangeName}")
    private String topicExchange;

    @Value("${spring.rabbitmq.queueName}")
    private String queueName;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    public Queue queue(){
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(topicExchange);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory container(ConnectionFactory connectionFactory,
                                                          MessageListenerAdapter listenerAdapter) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(messageConverter());
        return containerFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RabbitMQListener listener){
        return new MessageListenerAdapter(listener, "receivedMessage");
    }
}
