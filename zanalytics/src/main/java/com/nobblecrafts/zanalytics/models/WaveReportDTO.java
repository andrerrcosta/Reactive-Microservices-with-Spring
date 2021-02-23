package com.nobblecrafts.zanalytics.models;

import java.util.List;

import com.nobblecrafts.zanalytics.api.models.Point;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class WaveReportDTO {
    String id;
    String userId;
    String state;
    List<Point> fullWave;
    List<ReportObjectDomain<Point>> domains;
}