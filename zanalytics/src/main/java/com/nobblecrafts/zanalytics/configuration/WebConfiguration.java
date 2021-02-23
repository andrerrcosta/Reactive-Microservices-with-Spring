package com.nobblecrafts.zanalytics.configuration;

import com.nobblecrafts.zanalytics.api.Analytics;
import com.nobblecrafts.zanalytics.api.math.GeometricsService;
import com.nobblecrafts.zanalytics.api.utils.AnalyticsFilters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public Analytics analytics() {
        return new Analytics(new GeometricsService(), new AnalyticsFilters());
    }

}
