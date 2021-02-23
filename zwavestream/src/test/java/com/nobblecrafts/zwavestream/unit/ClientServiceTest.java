package com.nobblecrafts.zwavestream.unit;

import java.util.ArrayList;

import com.nobblecrafts.zwavestream.utils.TestSupplier;
import com.nobblecrafts.zwavestream.models.Wave;
import com.nobblecrafts.zwavestream.repository.WaveStreamRepository;
import com.nobblecrafts.zwavestream.services.WaveStreamService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private WaveStreamService service;
    
    @Mock
    private WaveStreamRepository repository;

    @Test
    @DisplayName("getAllWaveStreams")
    public void getAllWaveStreams() {
        var waves = new ArrayList<Wave>();
        for(var i = 0; i < 4; i++) {
            waves.add(TestSupplier.createRandomWave(10));
        }

        BDDMockito.when(repository.findAll())
            .thenReturn(Flux.fromIterable(waves));

        StepVerifier.create(service.getAllWaveStreams())
            .expectSubscription()
            .expectNextCount(waves.size())
            .verifyComplete();
    }

    /**
     * Oh... i'm tired :(
     */
}
