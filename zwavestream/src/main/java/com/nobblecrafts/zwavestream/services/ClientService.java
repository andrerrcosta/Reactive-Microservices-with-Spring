package com.nobblecrafts.zwavestream.services;

import java.util.List;

import com.nobblecrafts.zwavestream.models.Wave;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<Wave> getAllWaveStreams();

    Mono<Void> deleteAll();

    Mono<Wave> getWaveById(String id);

    Mono<Wave> saveWave(Wave wave);

    Mono<Void> deleteWaveStream(String waveId);

    Mono<Void> saveAll(List<Wave> waves);

}
