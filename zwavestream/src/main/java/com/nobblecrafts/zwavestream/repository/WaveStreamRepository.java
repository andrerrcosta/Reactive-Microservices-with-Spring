package com.nobblecrafts.zwavestream.repository;

import com.nobblecrafts.zwavestream.models.Wave;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WaveStreamRepository extends ReactiveMongoRepository<Wave, String> {
    Mono<Wave> findById(String id);

    @Query("{'waveId' : ?0 }")
    Flux<Wave> getWave(String waveId);
}
