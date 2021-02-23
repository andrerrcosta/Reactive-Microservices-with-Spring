package com.nobblecrafts.zanalytics.api.interfaces;

import java.util.List;
import java.util.Map;

import com.nobblecrafts.zanalytics.api.models.Point;

public interface AnalyticsReport {

    /**
     * Add an entry to the AnalyticReport map. If the supplied key is present
     * the value is added to its list.
     * @param domain
     * @param value
     */

    public void addDomain(Point domain, Integer value);

    /**
     * Set the AnalyticReport map with the supplied Map
     * @param domains
     */

    public void setDomains(Map<Point, List<Integer>> domains);

    /**
     * Sets the fullWave with a List of Points
     * @param fullWave
     */

    public void setFullWave(List<Point> fullWave);

    /**
     * @return The AnalyticReport map (Point, List<Point>)
     */
    public Map<Point, List<Integer>> getDomains();

    /**
     * Classify the AnalyticReport with an identifier String
     * @param state
     */
    public void classify(String state);
}
