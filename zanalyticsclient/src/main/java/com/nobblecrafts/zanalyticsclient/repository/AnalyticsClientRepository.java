package com.nobblecrafts.zanalyticsclient.repository;

import com.nobblecrafts.zanalyticsclient.models.WaveReportModel;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AnalyticsClientRepository extends ReactiveMongoRepository<WaveReportModel, String> {
    Mono<WaveReportModel> findWaveReportById(String id);

    @Query("{'userId' : ?0 }")
    Flux<WaveReportModel> findWaveReportByUser(String userId);
}
