package com.nobblecrafts.user.configuration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures RabbitMQ via AMQP abstraction to use events in our application.
 * 
 */
@Configuration
public class AMQPConfiguration {

    @Bean
    public TopicExchange userTopicExchange(@Value("${amqp.exchange.userstatus}") final String exchangeName) {
        System.out.println("USER Exchange created: " + exchangeName);
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    /**
     * Why Jackson here and what this bean will do?
     * 
     * By injecting a bean of type Jackson2JsonMessageConverter, we’re overriding
     * the default Java object serializer by a JSON object serializer. We do this to
     * avoid various pitfalls of the Java object serialization.
     * 
     * • It’s not a proper standard that we can use between programming languages.
     * If we would introduce a consumer that’s not written in Java, we have to look
     * for a specific library to perform cross-language deserialization.
     * 
     * • It uses a hard-coded fully qualified type name in the header of the
     * message. The deserializer expects the Java bean to be located in the same
     * package and to have the same name and fields. This is not flexible at all,
     * since we may want to deserialize only some properties and keep our own
     * version of the event data, following good domain-driven design practices.
     * 
     * JSON is not the only standard supported by Spring AMQP message converters.
     * You can also use XML or Google’s Protocol Buffers (a.k.a. protobuf). We’ll
     * stick to JSON in our system since it’s an extended standard, and it’s also
     * good for educational purposes because the payload is readable. In real
     * systems where performance is critical, you should consider an efficient
     * binary format (e.g., protobuf). See https://tpd.io/dataser for a comparison
     * of data serialization formats.
     * 
     */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
