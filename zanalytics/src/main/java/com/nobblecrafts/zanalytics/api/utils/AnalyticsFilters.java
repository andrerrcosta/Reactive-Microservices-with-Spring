package com.nobblecrafts.zanalytics.api.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.nobblecrafts.zanalytics.api.interfaces.AnalyticsReport;
import com.nobblecrafts.zanalytics.api.models.Point;

public class AnalyticsFilters {

    public boolean filterNonLinearWaves(Entry<Point, List<Integer>> entryset, Integer lastIndex) {

        return lastIndex - entryset.getKey().getIndex() == entryset.getValue().size()
                && entryset.getValue().stream().allMatch(point -> point > entryset.getKey().getIndex());
        
    }

    public Entry<Point, List<Integer>> filterReportWithStrategy(Entry<Point, List<Integer>> entry,
            AnalyticsReport report, Strategy strategy) {

        report.getDomains().put(entry.getKey(), entry.getValue());

        switch (strategy) {
        case BIGGEST_WAVE:

            report.getDomains()
                .entrySet()
                .stream()
                .max((a, b) -> a.getValue().size() - b.getValue().size())
                .map(v -> {
                    var map = new HashMap<Point, List<Integer>>();
                    map.put(v.getKey(), v.getValue());
                    return map;
                }).ifPresent(report::setDomains);

            return entry;

        case SMALLEST_WAVE:
            report.getDomains()
                .entrySet()
                .stream()
                .min((a, b) -> a.getValue().size() - b.getValue().size())
                .map(v -> {
                    var map = new HashMap<Point, List<Integer>>();
                    map.put(v.getKey(), v.getValue());
                    return map;
                }).ifPresent(report::setDomains);


            return entry;

        case ALL_PATTERN_WAVES:
            return entry;

        default:
            return entry;
        }
    }
}
