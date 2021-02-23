package com.nobblecrafts.user.controller;

import com.nobblecrafts.user.models.User;
import com.nobblecrafts.user.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> getAnyUserForTest() {
        return service.findById("6abg6707ghi31");
    }

}
