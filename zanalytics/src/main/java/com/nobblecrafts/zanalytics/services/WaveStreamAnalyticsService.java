package com.nobblecrafts.zanalytics.services;

import com.nobblecrafts.zanalytics.api.Analytics;
import com.nobblecrafts.zanalytics.api.models.WaveReport;
import com.nobblecrafts.zanalytics.api.models.WaveStream;
import com.nobblecrafts.zanalytics.api.utils.Strategy;
import com.nobblecrafts.zanalytics.utils.WaveReportMapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaveStreamAnalyticsService implements WaveStreamAnalytics {

    private final Analytics analytics;
    private final AnalyticsPublisher publisher;
    private final WaveReportMapper mapper = new WaveReportMapper();

    @Override
    public void analyzeWaveStream(WaveStream wave) {
        analytics.check(wave)
            .forKnownCheatingWaves()
            .generateReport(new WaveReport(wave.getId(), wave.getUserId()), Strategy.BIGGEST_WAVE)
            .then(report -> publisher.publishWaveReport(mapper.convertFromReportToDTO((WaveReport) report)));
    }
}