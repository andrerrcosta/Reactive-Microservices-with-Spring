package com.nobblecrafts.zanalytics.api.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyticsObjectStream {

    private WaveStream waveStream;
    private Map<Point, List<Integer>> domains;

    public AnalyticsObjectStream(WaveStream waveStream) {
        this.waveStream = waveStream;
        this.domains = new HashMap<>();
    }

    public Point getLastPointOfWave() {
        return this.waveStream.getPoints().get(this.waveStream.getPoints().size() - 1);
    }

    public void addPointToDomain(Point domain, Integer pointIndex) {
        if(domains.containsKey(domain)) {
            domains.get(domain).add(pointIndex);
        } else {
            var list = new LinkedList<Integer>();
            list.add(pointIndex);
            domains.put(domain, list);
        }
    }
}
