package com.nobblecrafts.zwavestream.configuration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Configures RabbitMQ via AMQP abstraction to use events in our application.
 */

@Slf4j
@Configuration
public class AMQPConfiguration {

    @Bean
    public TopicExchange userTopicExchange(@Value("${amqp.exchange.wavestream}") final String exchangeName) {
        log.info("Wave Stream Exchange created: {} ", exchangeName);
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Até então a wave stream não vai receber nada de volta de nenhum serviço.
     * então ela não precisa de uma pilha. Apenas de um exchange e de um publisher.
     */

    // @Bean
    // public Queue panelQueue(@Value("${amqp.queue.wavestream}") final String
    // queueName) {
    // return QueueBuilder.durable(queueName).ttl((int)
    // Duration.ofHours(6).toMillis()).maxLength(25000).build();
    // }

    // @Bean
    // public Binding correctAttemptsBinding(final Queue panelQueue, final
    // TopicExchange exchangeName,
    // @Value("${amqp.routingkey.wavestream}") final String routingkey) {
    // return BindingBuilder.bind(panelQueue).to(exchangeName).with(routingkey);
    // }

    // @Bean
    // public RabbitListenerConfigurer rabbitListenerConfigurer(
    // final MessageHandlerMethodFactory messageHandlerMethodFactory) {
    // return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    // }

}
