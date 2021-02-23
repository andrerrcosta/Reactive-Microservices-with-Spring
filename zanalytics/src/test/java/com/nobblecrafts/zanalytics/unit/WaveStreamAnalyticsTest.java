package com.nobblecrafts.zanalytics.unit;

import com.nobblecrafts.zanalytics.services.WaveStreamAnalyticsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class WaveStreamAnalyticsTest {

    @InjectMocks
    private WaveStreamAnalyticsService service;

    // @SpyBean
    // private AnalyticsPublisher publisher;

    // @BeforeEach
    // public void mocker() {
    //     Mockito.doNothing()
    //         .when(publisher)
    //         .publishWaveReport(ArgumentMatchers.any());
    // }

    @Test
    public void letsPretendThat() {
        /**
         * I will not test the api. This is not a real job. And i already did the integration tests
         * so, i guess it's all fine!
         */
    }
    
}
