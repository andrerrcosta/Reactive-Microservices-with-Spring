package com.nobblecrafts.zanalyticsclient.controller;

import com.nobblecrafts.zanalyticsclient.models.WaveReportModel;
import com.nobblecrafts.zanalyticsclient.services.AnalyticsClientService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analyticsclient")
public class AnalyticsClientController {

    private final AnalyticsClientService service;

    @GetMapping(path = "{id}")
    public Mono<WaveReportModel> getWaveReportById(@PathVariable String id) {
        return service.findWaveReportById(id);
    }

    @GetMapping(path = "user/{userId}")
    public Flux<WaveReportModel> getUserWaveReports(@PathVariable String userId) {
        return service.getUserWaveReports(userId);
    }
}
