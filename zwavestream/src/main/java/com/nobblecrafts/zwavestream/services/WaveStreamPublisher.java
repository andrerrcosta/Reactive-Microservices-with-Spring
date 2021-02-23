package com.nobblecrafts.zwavestream.services;

import com.nobblecrafts.zwavestream.models.Wave;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WaveStreamPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;
    private final String waveStreamRoutingKey;

    public WaveStreamPublisher(final AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.wavestream}") final String exchangeName, 
            @Value("${amqp.routingkey.wavestream}") final String routingkey ) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
        this.waveStreamRoutingKey = routingkey;
    }

    public Mono<Wave> publishWaveStream(final Wave wave) {
        try {
            amqpTemplate.convertAndSend(exchangeName, waveStreamRoutingKey, wave);
            log.info("WaveStream: message sent");
            log.info("\nexchangeName: {}\nroutingKey: {}\nmessage: {}", exchangeName, waveStreamRoutingKey, wave);
        } catch (Error e) {
            log.error("Error publishing wave stream\n{}", e);
        }
        return Mono.just(wave);
    }

}
