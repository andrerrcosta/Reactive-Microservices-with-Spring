package com.nobblecrafts.zanalyticsclient.services;

import com.nobblecrafts.zanalyticsclient.models.WaveReportModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AnalyticsClient {

    public Mono<WaveReportModel> findWaveReportById(String id);

    public Flux<WaveReportModel> getUserWaveReports(String userId);

    public Mono<WaveReportModel> saveWaveReport(WaveReportModel waveReportModel);
}
