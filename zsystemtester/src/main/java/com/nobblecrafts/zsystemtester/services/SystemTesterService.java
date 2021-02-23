package com.nobblecrafts.zsystemtester.services;

import com.nobblecrafts.zsystemtester.api.SystemTester;
import com.nobblecrafts.zsystemtester.models.TestReport;
import com.nobblecrafts.zsystemtester.models.Wave;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SystemTesterService implements TesterService {

    private final WebClient client;
    private final SystemTester tester;
    private final SystemTesterSupplier supplier;

    public Mono<Void> saveWave(Wave wave) {
        return client
            .method(HttpMethod.POST)
            .uri("/save/", supplier.createRandomWave(20))
            .retrieve()
            .bodyToMono(Wave.class)
            .then();
    }

    @Override
    public <T extends TestReport> Mono<Void> bindAsyncEvent(T obj) {
        return tester.observe(obj);
    }

}
