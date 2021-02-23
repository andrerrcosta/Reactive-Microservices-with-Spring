package com.nobblecrafts.user.services;

import com.nobblecrafts.user.models.User;
import com.nobblecrafts.user.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements ClientService {

    private final UserRepository repository;
    // private final UserPublisher publisher;

    @Override
    public Flux<User> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        return this.repository.findById(id).switchIfEmpty(monoResponseStatusNotFoundException());
    }

    @Override
    public Mono<User> save(User user) {
        return this.repository.save(user);
    }

    @Transactional
    @Override
    public Mono<Void> update(User user) {
        return this.findById(user.getId())
                    .map(u -> user.withId(u.getId()))
                    .flatMap(repository::save)
                    .thenEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.findById(id).flatMap(repository::delete);
    }

    public <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Spring also supports RabbitMQ transactions, both on the publisher and
     * subscriber sides. When we send messages with AmqpTemplate or its
     * RabbitTemplate implementation within the scope of a method annotated
     * with @Transactional and when we enable transactionality in the channel
     * (RabbitMQ), these messages don’t reach the broker if an exception happens
     * even after the method call to send them. On the consumer’s side, it’s also
     * possible to use transactions to reject messages that have been already
     * processed. In this case, the queue would need to be set up to requeue the
     * rejected messages (which is the default behavior). The section Transactions
     * in the Spring AMQP documentation explains how they work in detail; see
     * https://docs.spring.io/spring-amqp/reference/html/#transactions
     * 
     * In many cases like ours, we can simplify the strategy for transactions and
     * limit it only to the database.
     * 
     * • While publishing, if we have only one broker operation, we can publish the
     * message at the end of the process. Any error that happens before or while
     * sending the message will cause the rollback of the database operation.
     * 
     * • On the subscriber’s side, the message will be rejected by default if there
     * is an exception, and we can requeue it if that’s what we want. Then, we could
     * also use the Transactional annotation in our newAttemptForUser’s service
     * method, so database operations will be rolled back too in case of a failure.
     * 
     * Local transactionality within a microservice is critical to keep data
     * consistent and avoid partially completed processes inside a domain.
     * Therefore, you should think of things that can go wrong in your business
     * logic when it entails multiple steps with possible interactions with external
     * components such as a database or a message broker.
     */

}
