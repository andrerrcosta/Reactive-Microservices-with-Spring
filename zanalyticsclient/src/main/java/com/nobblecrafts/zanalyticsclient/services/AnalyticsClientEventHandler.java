package com.nobblecrafts.zanalyticsclient.services;

import com.nobblecrafts.zanalyticsclient.models.WaveReportModel;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalyticsClientEventHandler {

    private final AnalyticsClientService service;

    @RabbitListener(queues = "${amqp.queue.analyticsclient}")
    public void handleWaveReport(final WaveReportModel event) {
        try {
            log.info("Received analytics report event {}", event);
            service.saveWaveReport(event)
                .subscribe();
        } catch (final Exception e) {
            log.error("Error when trying to process handleWaveReport {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
