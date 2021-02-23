package com.nobblecrafts.zwavestream.unit;

import com.nobblecrafts.zwavestream.utils.TestSupplier;
import com.nobblecrafts.zwavestream.controller.ZWaveStreamController;
import com.nobblecrafts.zwavestream.services.WaveStreamService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class ControllerTest {

    @InjectMocks
    private ZWaveStreamController controller;

    @Mock
    private WaveStreamService service;
    
    @Test
    @DisplayName("getWaveStream")
    public void getWaveStream() {
        var wave = TestSupplier.createRandomWave(10);

        BDDMockito.when(service.getWaveById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(wave));

        StepVerifier.create(controller.getWaveStream("x"))
            .expectSubscription()
            .expectNext(wave)
            .verifyComplete();
    }

    @Test
    @DisplayName("saveWaveStream")
    public void saveWaveStream() {
        var wave = TestSupplier.createRandomWave(10);

        BDDMockito.when(service.saveWave(ArgumentMatchers.any()))
            .thenReturn(Mono.just(wave));

        StepVerifier.create(controller.saveWaveStream(wave))
            .expectSubscription()
            .expectNext(wave)
            .verifyComplete();
    }


}
