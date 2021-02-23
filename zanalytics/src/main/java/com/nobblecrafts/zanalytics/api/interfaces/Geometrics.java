package com.nobblecrafts.zanalytics.api.interfaces;

import java.util.List;
import java.util.stream.Stream;

import com.nobblecrafts.zanalytics.api.utils.ObjectAttribute;
import com.nobblecrafts.zanalytics.api.models.Point;

public interface Geometrics {

    /**
     * Returns a Stream of ObjectAttribute (Point, Integer) with a classification of
     * the range given as a straight line and the points in the list that belong to
     * that line. The start is the domain and is verified as an iterable Stream of
     * points with each points from the list.
     * 
     * Complexity: (O^2)
     * 
     * @param tuple
     * @param wave
     * @return
     */

    public Stream<ObjectAttribute<Point, Integer>> straightLineStream(Point start, Point end, List<Point> wave);

    /**
     * Returns an ObjectAttribute (Point, Point) when the point belongs to the same
     * line of the given domain and the last point of the wave. Returns null when it
     * doesn't;
     * 
     * @param start
     * @param end
     * @param point
     * @return
     */

    public ObjectAttribute<Point, Integer> belongsToTheSameLine(Point start, Point end, Point point);
}
