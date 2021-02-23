package com.nobblecrafts.zanalytics.services;

import com.nobblecrafts.zanalytics.api.models.WaveStream;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class WaveStreamAnalyticsEventHandler {

    private final WaveStreamAnalyticsService service;

    @RabbitListener(queues = "${amqp.queue.analytics}")
    public void handleWaveStream(final WaveStream event) {
        // log.info("Analytics Event Received {}", event);
        try {
            service.analyzeWaveStream(event);
        } catch (final Exception e) {
            log.error("Error when trying to process handleWaveStream {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}
