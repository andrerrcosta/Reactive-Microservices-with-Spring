package com.nobblecrafts.zanalyticsclient.unit;

import com.nobblecrafts.zanalyticsclient.controller.AnalyticsClientController;
import com.nobblecrafts.zanalyticsclient.services.AnalyticsClientService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ControllerTest {
    
    @InjectMocks
    private AnalyticsClientController controller;

    @Mock
    private AnalyticsClientService service;

    @DisplayName("maybeLater")
    @Test
    public void maybeLater() {
        /**
         * Maybe Later
         */
    }
}
