package com.nobblecrafts.user.unit;

import com.nobblecrafts.user.controller.UserController;
import com.nobblecrafts.user.services.UserService;
import com.nobblecrafts.user.utils.TestSupplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @DisplayName("getAnyUserForTest")
    @Test
    public void getAnyUserForTest() {
        var user = TestSupplier.createRandomValidUser();

        BDDMockito.when(service.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(user));

        StepVerifier
            .create(service.findById("x"))
            .expectSubscription()
            .expectNext(user)
            .verifyComplete();
    }
}
