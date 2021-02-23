package com.nobblecrafts.zwavestream.services;

import java.util.List;

import com.nobblecrafts.zwavestream.models.Wave;
import com.nobblecrafts.zwavestream.repository.WaveStreamRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaveStreamService implements ClientService {

    private final WaveStreamRepository repository;
    private final WaveStreamPublisher publisher;

    @Override
    public Flux<Wave> getAllWaveStreams() {
        return this.repository.findAll();
    }

    @Override
    public Mono<Void> deleteAll() {
        return repository.deleteAll()
            .thenEmpty(Mono.empty());
    }

    @Override
    public Mono<Wave> getWaveById(String id) {
        return this.repository.findById(id)
            .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    @Override
    public Mono<Wave> saveWave(Wave wave) {
        log.info("Saving wave {}", wave.toString());
        return this.repository.save(wave)
            .flatMap(publisher::publishWaveStream);
    }

    @Override
    public Mono<Void> deleteWaveStream(String waveId) {
        return this.getWaveById(waveId)
            .flatMap(this.repository::delete)
            .then(Mono.empty());
    }

    @Transactional
    @Override
    public Mono<Void> saveAll(List<Wave> waves) {
        log.info("saveAll waveStream {}", waves);
        return repository.saveAll(waves)
            .flatMap(this.publisher::publishWaveStream)
            .then(Mono.empty());
    }

    public <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Wave not found"));
    }

}
