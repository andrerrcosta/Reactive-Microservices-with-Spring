package com.nobblecrafts.zwavestream.controller;

import javax.validation.Valid;

import com.nobblecrafts.zwavestream.models.Wave;
import com.nobblecrafts.zwavestream.services.WaveStreamService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wavestream")
public class ZWaveStreamController {

    private final WaveStreamService service;

    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public Flux<Wave> getAllWaveObjects() {
    //     return service.getAllWaveStreams();
    // }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Wave> getWaveStream(@PathVariable String id) {
        log.info("Get Wave Stream {}", id);
        return service.getWaveById(id);
    }

    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Wave> saveWaveStream(@RequestBody @Valid Wave wave) {
        log.info("Save Wave {}", wave);
        return this.service.saveWave(wave);
    }

    // @DeleteMapping("deleteAll")
    // @ResponseStatus(HttpStatus.CREATED)
    // public Mono<Void> deleteAll() {
    //     return this.service.deleteAll();
    // }

    // @PostMapping("test")
    // @ResponseStatus(HttpStatus.CREATED)
    // public Mono<Void> test(@RequestBody @Valid WavePoint point) {
    // return this.service.test();
    // }

    // @GetMapping("test")
    // @ResponseStatus(HttpStatus.OK)
    // public Flux<Wave> getAllTest() {
    // return service.getAllWaveStreams();

    // }

    // private Wave createWaveTest() {
    // return Wave.builder().points(List.of(new WavePoint(1, 2, 3, 1), new
    // WavePoint(1, 2, 3, 2))).build();
    // }

    // private WavePoint createWavePoint() {
    // return new WavePoint(new Random().nextInt(100), new Random().nextInt(100),
    // new Random().nextInt(100),
    // new Random().nextInt(100));
    // }

}
