package com.nobblecrafts.zanalytics.integration.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nobblecrafts.zanalytics.api.Analytics;
import com.nobblecrafts.zanalytics.api.models.Point;
import com.nobblecrafts.zanalytics.api.models.WaveReport;
import com.nobblecrafts.zanalytics.api.utils.Strategy;
import com.nobblecrafts.zanalytics.utils.TestSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class AnalyticsTest {

    @Autowired
    private Analytics analytics;

    private Map<Point, List<Integer>> map = new HashMap<>();

    @Test
    void testForAllPatternLinearWaves() {

        int waveSize = 10;
        
        analytics.check(TestSupplier.createLinearWave(waveSize)).forKnownCheatingWaves()
                .generateReport(new WaveReport(), Strategy.ALL_PATTERN_WAVES)
                .then(a -> map = a.getDomains());

        Assertions.assertEquals(waveSize -1, map.size());

        log.info("DOMAINS {}", map);

    }

    @Test
    void testForSmallestLinearWaves() {

        int waveSize = 10;
        
        analytics.check(TestSupplier.createLinearWave(waveSize)).forKnownCheatingWaves()
                .generateReport(new WaveReport(), Strategy.SMALLEST_WAVE)
                .then(a -> map = a.getDomains());

        Assertions.assertEquals(map.size(), 1);
        map.keySet().stream()
            .forEach(v -> {
                Assertions.assertEquals(v.getIndex(), waveSize - 2);
            });
        
        log.info("DOMAINS {}", map);
    }

    @Test
    void testForBiggestLinearWaves() {

        int waveSize = 10;
        
        analytics.check(TestSupplier.createLinearWave(waveSize)).forKnownCheatingWaves()
                .generateReport(new WaveReport(), Strategy.BIGGEST_WAVE)
                .then(a -> map = a.getDomains());

        Assertions.assertEquals(map.size(), 1);
        map.keySet().stream()
            .forEach(v -> {
                Assertions.assertEquals(v.getIndex(), 0);
            });
        
        log.info("DOMAINS {}", map);

    }

    @Test
    void testForBiggestRandomWaves() {

        int waveSize = 10;
        
        analytics.check(TestSupplier.createRandomWave(waveSize)).forKnownCheatingWaves()
                .generateReport(new WaveReport(), Strategy.BIGGEST_WAVE)
                .then(a -> map = a.getDomains());

        Assertions.assertTrue(map.size() >= 1);
        
        log.info("DOMAINS {}", map);

    }

}
