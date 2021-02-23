package com.nobblecrafts.zanalytics.api;

import java.util.function.Consumer;

import com.nobblecrafts.zanalytics.api.interfaces.AnalyticsReport;
import com.nobblecrafts.zanalytics.api.interfaces.Geometrics;
import com.nobblecrafts.zanalytics.api.models.AnalyticsObjectStream;
import com.nobblecrafts.zanalytics.api.models.WaveStream;
import com.nobblecrafts.zanalytics.api.utils.AnalyticsFilters;
import com.nobblecrafts.zanalytics.api.utils.Strategy;

import org.springframework.util.Assert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class Analytics {

    private final Geometrics geometrics;
    private final AnalyticsFilters filters;
    private AnalyticsObjectStream stream;
    private AnalyticsReport report;

    public Analytics check(WaveStream wave) {
        Assert.notNull(wave, "Wave must not be null");
        this.stream = new AnalyticsObjectStream(wave);
        return this;
    }

    public Analytics forKnownCheatingWaves() {

        log.info("Analizing Wave {} for knownCheatingWaves", stream.getWaveStream().getId());

        stream.getWaveStream().getPoints().stream()
                .flatMap(point -> geometrics.straightLineStream(point, stream.getLastPointOfWave(),
                        stream.getWaveStream().getPoints()))
                .forEach(tuple -> {
                    stream.addPointToDomain(tuple.getKey(), tuple.getAttribute());
                    // log.info("forKnow {}", stream.getDomains());
                });

        return this;
    }

    public Analytics generateReport(AnalyticsReport report, Strategy strategy) {
        Assert.notNull(report, "Report must not be null");
        log.info("Generate Report {}", stream.getWaveStream().getId());

        this.report = report;
        this.report.setFullWave(stream.getWaveStream().getPoints());
        
        stream.getDomains().entrySet().stream()
                .filter(entryset -> filters.filterNonLinearWaves(entryset, stream.getLastPointOfWave().getIndex()))
                .forEach(entry -> filters.filterReportWithStrategy(entry, report, strategy));

        this.report.classify(this.report.getDomains().size() > 0 ? "Pattern Found" : "No Pattern Was Found");

        return this;
    }

    public Analytics then(Consumer<AnalyticsReport> consumer) {
        consumer.accept(report);
        return this;
    }

}