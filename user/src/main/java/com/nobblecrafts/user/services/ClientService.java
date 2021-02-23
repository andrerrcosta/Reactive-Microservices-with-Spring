package com.nobblecrafts.user.services;

import com.nobblecrafts.user.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    /**
     * Finds all users
     * @return Flux of user
     */
    public Flux<User> findAll();


    /**
     * Find an user by id
     * @param id
     * @return
     */
    public Mono<User> findById(String id);


    /**
     * Save an user
     * @param user
     * @return Mono of user
     */
    public Mono<User> save(User user);


    /**
     * Update an user
     * @param user
     * @return Mono empty
     */
    public Mono<Void> update(User user);


    /**
     * Delete an user
     * @param id
     * @return Mono empty
     */
    public Mono<Void> delete(String id);

}
