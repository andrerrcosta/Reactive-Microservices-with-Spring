package com.nobblecrafts.zanalytics.api.math;

import java.util.List;
import java.util.stream.Stream;

import com.nobblecrafts.zanalytics.api.interfaces.Geometrics;
import com.nobblecrafts.zanalytics.api.utils.ObjectAttribute;
import com.nobblecrafts.zanalytics.api.models.Point;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GeometricsService implements Geometrics {

    public Stream<ObjectAttribute<Point, Integer>> straightLineStream(Point start, Point end, List<Point> wave) {
        return wave.stream()
            .filter(point -> point.getIndex() > start.getIndex())
            .map(point -> this.belongsToTheSameLine(start, end, point))
            .filter(tuple -> tuple != null);
    }

    public ObjectAttribute<Point, Integer> belongsToTheSameLine(Point start, Point end, Point point) {
        int line = (point.getX() * start.getY()) + (point.getY() * end.getX())
                + (start.getX() * end.getY()) - (start.getY() * end.getX()) - (point.getY() * start.getX())
                - (point.getX() * end.getY());

        return line == 0 ? new ObjectAttribute<>(start, point.getIndex()) : null;
    }


}
