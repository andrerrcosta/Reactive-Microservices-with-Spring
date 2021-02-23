package com.nobblecrafts.zsystemtester.configurations;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
// import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * I don't want to implement this service. It will throw a Fatal Message Conversion error
 * This is because I didn't even create the models to receive the messages. so, i'm disabling
 * the topics and the queues.
 * 
 * Maybe LAter
 */

@Slf4j
@Configuration
public class AMQPConfiguration {

    // @Bean
    // public TopicExchange waveStreamTopicExchange(@Value("${amqp.exchange.wavestream}") final String exchangeName) {
    //     log.info("Wave Stream Exchange created: " + exchangeName);
    //     return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    // }

    // @Bean
    // public TopicExchange analyticsReportTopicExchange(@Value("${amqp.exchange.analytics}") final String exchangeName) {
    //     log.info("Analytics Report Exchange created: " + exchangeName);
    //     return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    // }

    // @Bean
    // public Queue panelQueue(@Value("${amqp.queue.systemtester}") final String queueName) {
    //     return QueueBuilder.durable(queueName).ttl((int) Duration.ofHours(1).toMillis()).maxLength(25000).build();
    // }

    // @Bean
    // public Binding handleWaveStream(final Queue panelQueue,
    //         @Qualifier("waveStreamTopicExchange") final TopicExchange exchangeName,
    //         @Value("${amqp.routingkey.wavestream}") final String routingKey) {
    //     return BindingBuilder.bind(panelQueue).to(exchangeName).with(routingKey);
    // }

    // @Bean
    // public Binding handleAnalyticsReport(final Queue panelQueue,
    //         @Qualifier("analyticsReportTopicExchange") final TopicExchange exchangeName,
    //         @Value("${amqp.routingkey.analytics}") final String routingKey) {
    //     return BindingBuilder.bind(panelQueue).to(exchangeName).with(routingKey);
    // }

    // @Bean
    // public RabbitListenerConfigurer rabbitListenerConfigurer(
    //         final MessageHandlerMethodFactory messageHandlerMethodFactory) {
    //     return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    // }

    // @Bean
    // public MessageHandlerMethodFactory messageHandlerMethodFactory() {
    //     DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    //     final MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
    //     jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

    //     factory.setMessageConverter(jsonConverter);
    //     return factory;
    // }

    // @Bean
    // public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    // return new Jackson2JsonMessageConverter();
    // }
}
