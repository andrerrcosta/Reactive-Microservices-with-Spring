package com.nobblecrafts.zanalytics.api.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nobblecrafts.zanalytics.api.interfaces.AnalyticsReport;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Getter
@Setter
@With
@Component
@AllArgsConstructor
@ToString
public class WaveReport implements AnalyticsReport {

    private String id;
    private String state;
    private String userId;
    private List<Point> fullWave;
    private Map<Point, List<Integer>> domains;

    public WaveReport() {
        this.domains = new HashMap<>();
        this.fullWave = new LinkedList<>();
    }

    public WaveReport(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.domains = new HashMap<>();
        this.fullWave = new LinkedList<>();
    }

    @Override
    public void addDomain(Point domain, Integer value) {
        if (domains.containsKey(domain)) {
            domains.get(domain).add(value);
        } else {
            List<Integer> values = new LinkedList<>();
            values.add(value);
            domains.put(domain, values);
        }
    }

    @Override
    public void classify(String state) {
        this.state = state;
    }
}
