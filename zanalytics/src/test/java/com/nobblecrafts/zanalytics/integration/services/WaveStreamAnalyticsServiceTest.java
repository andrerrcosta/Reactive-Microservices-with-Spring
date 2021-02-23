package com.nobblecrafts.zanalytics.integration.services;

import com.nobblecrafts.zanalytics.api.Analytics;
import com.nobblecrafts.zanalytics.services.AnalyticsPublisher;
import com.nobblecrafts.zanalytics.services.WaveStreamAnalyticsService;
import com.nobblecrafts.zanalytics.utils.TestSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WaveStreamAnalyticsServiceTest {

    @Autowired
    private WaveStreamAnalyticsService service;

    @Autowired
    private Analytics analytics;

    @SpyBean
    private AnalyticsPublisher publisher;

    @BeforeEach
    public void setup() {
        Mockito.doNothing()
            .when(publisher)
            .publishWaveReport(ArgumentMatchers.any());
    }

    @Test
    void testForAnalyzeWaveStream() {

        int waveSize = 60;

        service.analyzeWaveStream(TestSupplier.createLinearWave(waveSize));

        Assertions.assertEquals(analytics.getReport().getDomains().size(), 1);
        analytics.getReport().getDomains().keySet().stream()
            .forEach(v -> Assertions.assertEquals(v.getIndex(), 0));

        log.info("report {}", analytics.getReport().getDomains());
    }

}
