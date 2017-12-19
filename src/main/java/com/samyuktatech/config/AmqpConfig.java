package com.samyuktatech.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.samyuktatech.amqp.AMQPLocationListener;

@Configuration
public class AmqpConfig {

	final static String queueName = "location";
	
	@Bean
    Queue queue() {
        return new Queue(queueName, true);
    }
    
    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange("exchange");
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueues(queue());
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    public ConnectionFactory connectionFactory() {
    	CachingConnectionFactory connectionFactory = new CachingConnectionFactory("10.50.1.148");
    	connectionFactory.setUsername("test");
    	connectionFactory.setPassword("test");
    	return connectionFactory;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AMQPLocationListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
