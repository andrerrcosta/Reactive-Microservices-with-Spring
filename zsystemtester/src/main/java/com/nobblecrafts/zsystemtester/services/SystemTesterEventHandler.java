package com.nobblecrafts.zsystemtester.services;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.nobblecrafts.zsystemtester.models.ServiceReport;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemTesterEventHandler {

    private final TesterService service;

    @RabbitListener(queues = "${amqp.queue.systemtester}")
    public void handleReport(final Default event) {
        try {
            log.info("Received analytics report event {}", event);
            service.bindAsyncEvent(new ServiceReport())
                .subscribe();

        } catch (final Exception e) {
            log.error("Error when trying to process handleReport {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
    
}
