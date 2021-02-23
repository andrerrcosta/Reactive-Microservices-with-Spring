package com.nobblecrafts.zwavestream.integration;

import java.util.Date;
import java.util.UUID;

import com.nobblecrafts.zwavestream.exception.CustomAttributes;
import com.nobblecrafts.zwavestream.models.Wave;
import com.nobblecrafts.zwavestream.repository.WaveStreamRepository;
import com.nobblecrafts.zwavestream.services.WaveStreamPublisher;
import com.nobblecrafts.zwavestream.services.WaveStreamService;
import com.nobblecrafts.zwavestream.utils.TestSupplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import({WaveStreamService.class, WaveStreamPublisher.class, CustomAttributes.class})
public class ZWaveStreamServiceTest {

    @MockBean
    private WaveStreamRepository repository;

    @MockBean
    private WaveStreamPublisher publisher;

    @Autowired
    private WebTestClient client;

    @Test
    @DisplayName("get Wave Stream and found")
    public void getWaveStream() {
        var wave = TestSupplier.createLinearWave(2)
            .withId(UUID.randomUUID().toString())
            .withCreatedDate(new Date())
            .withAction("action");

        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(wave));

        client.get()
            .uri("/wavestream/{id}", "x")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Wave.class)
            .isEqualTo(wave);
    }

    @Test
    @DisplayName("get Wave Stream and not found")
    public void getWaveStreamNotFound() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.empty());

        client.get()
            .uri("/wavestream/{id}", "x")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    @DisplayName("Save Wave Stream With a Valid Wave")
    public void saveWaveStream() {
        var wave = TestSupplier.createValidWave(10);  
            
        BDDMockito.when(repository.save(ArgumentMatchers.any()))
            .thenReturn(Mono.just(wave));

        BDDMockito.when(publisher.publishWaveStream(ArgumentMatchers.any()))
            .thenReturn(Mono.just(wave));

        client.post()
            .uri("/wavestream/save")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(wave)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Wave.class)
            .isEqualTo(wave);
    }

    @Test
    @DisplayName("Save Wave Stream With a Invalid Wave")
    public void saveInvalidWaveStream() {
        var wave = TestSupplier.createInvalidWave(10);  
            
        BDDMockito.when(repository.save(ArgumentMatchers.any()))
            .thenReturn(Mono.just(wave));

        BDDMockito.when(publisher.publishWaveStream(ArgumentMatchers.any()))
            .thenReturn(Mono.just(wave));

        client.post()
            .uri("/wavestream/save")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(wave)
            .exchange()
            .expectStatus().is4xxClientError()
            .expectBody()
            .jsonPath("$.status").isEqualTo(400);
    }
    
}
