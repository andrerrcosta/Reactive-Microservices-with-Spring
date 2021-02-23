package com.nobblecrafts.zanalytics.services;

import com.nobblecrafts.zanalytics.models.WaveReportDTO;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnalyticsPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;
    private final String analyticsReportRoutingKey;

    public AnalyticsPublisher(final AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.analytics}") final String exchangeName,
            @Value("${amqp.routingkey.analytics}") final String analyticsReportRoutingKey) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
        this.analyticsReportRoutingKey = analyticsReportRoutingKey;
    }

    public void publishWaveReport(final WaveReportDTO report) {
        try {
            amqpTemplate.convertAndSend(exchangeName, analyticsReportRoutingKey, report);
            log.info("ANALYTICS-PUBLISHER: message sent");
            log.info("\nexchangeName: {}\nroutingKey: {}\nmessage: {}", exchangeName, analyticsReportRoutingKey,
                    report);
        } catch (Error e) {
            log.error("Error publishing WaveReport {}", e);
        }
    }
}
