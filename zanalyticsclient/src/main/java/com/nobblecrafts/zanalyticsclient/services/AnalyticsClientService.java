package com.nobblecrafts.zanalyticsclient.services;

import com.nobblecrafts.zanalyticsclient.models.WaveReportModel;
import com.nobblecrafts.zanalyticsclient.repository.AnalyticsClientRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsClientService implements AnalyticsClient {

    private final AnalyticsClientRepository repository;

    @Override
    public Mono<WaveReportModel> findWaveReportById(String id) {
        return repository.findWaveReportById(id);
    }

    @Override
    public Flux<WaveReportModel> getUserWaveReports(String userId) {
        return repository.findWaveReportByUser(userId);
    }

    @Override
    public Mono<WaveReportModel> saveWaveReport(WaveReportModel waveReportModel) {
        log.info("Saving WaveReportModel {}", waveReportModel);
        return this.repository.save(waveReportModel);
    }

}
