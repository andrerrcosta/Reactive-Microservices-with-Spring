package com.nobblecrafts.zsystemtester.services;

import com.nobblecrafts.zsystemtester.models.TestReport;

import reactor.core.publisher.Mono;

public interface TesterService {
    public <T extends TestReport> Mono<Void> bindAsyncEvent(T obj);
}
