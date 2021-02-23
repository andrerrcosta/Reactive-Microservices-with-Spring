package com.nobblecrafts.user.services;

import com.nobblecrafts.user.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    public Flux<User> findAll();

    public Mono<User> findById(String id);

    public Mono<User> save(User user);

    public Mono<Void> update(User user);

    public Mono<Void> delete(String id);

}
