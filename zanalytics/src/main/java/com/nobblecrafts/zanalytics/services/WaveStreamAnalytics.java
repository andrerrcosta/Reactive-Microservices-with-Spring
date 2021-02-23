package com.nobblecrafts.zanalytics.services;

import com.nobblecrafts.zanalytics.api.models.WaveStream;

public interface WaveStreamAnalytics {
    /**
     * Analyze a WaveStream event object and publish on the queue the results
     * 
     * @param event
     */
    public void analyzeWaveStream(WaveStream event);
}
