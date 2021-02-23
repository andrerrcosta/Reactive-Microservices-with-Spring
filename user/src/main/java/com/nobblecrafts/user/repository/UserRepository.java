package com.nobblecrafts.user.repository;

import com.nobblecrafts.user.models.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findById(Long id);
}
