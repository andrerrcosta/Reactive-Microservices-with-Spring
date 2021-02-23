package com.nobblecrafts.user.unit;

import java.util.List;

import com.nobblecrafts.user.models.User;
import com.nobblecrafts.user.repository.UserRepository;
import com.nobblecrafts.user.services.UserService;
import com.nobblecrafts.user.utils.TestSupplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private List<User> randomUsers;
    private User randomUser;

    @BeforeEach
    public void setup() {
        randomUsers = TestSupplier.createListOfRandomValidUsers(5);
        randomUser = TestSupplier.createRandomValidUser();
    }

    @DisplayName("Find All Users")
    @Test
    public void findAll() {
        BDDMockito.when(repository.findAll())
        .thenReturn(Flux.fromIterable(randomUsers));
        StepVerifier
            .create(service.findAll())
            .expectSubscription()
            .expectNextCount(randomUsers.size())
            .verifyComplete();
    }

    @DisplayName("Find By Id")
    @Test
    public void findById() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(randomUser));

        StepVerifier
            .create(service.findById("x"))
            .expectSubscription()
            .expectNext(randomUser)
            .verifyComplete();
    }

    @DisplayName("Find By Id and Expect Error")
    @Test
    public void findByIdWithEmptyResponse() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.empty());

        StepVerifier
            .create(service.findById("x"))
            .expectSubscription()
            .expectError(ResponseStatusException.class)
            .verify();
    }

    @DisplayName("Save Valid User")
    @Test
    public void saveValidUser() {
        BDDMockito.when(repository.save(ArgumentMatchers.any()))
            .thenReturn(Mono.just(randomUser));

        StepVerifier
            .create(service.save(randomUser))
            .expectSubscription()
            .expectNext(randomUser)
            .verifyComplete();
    }

    @DisplayName("Update User")
    @Test
    public void updateUser() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(randomUser));

        BDDMockito.when(repository.save(ArgumentMatchers.any()))
            .thenReturn(Mono.just(randomUser));

        StepVerifier
            .create(service.update(randomUser))
            .expectSubscription()
            .verifyComplete();
    }

    @DisplayName("Update User Not Found")
    @Test
    public void updateUserNotFound() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.empty());

        StepVerifier
            .create(service.update(randomUser))
            .expectSubscription()
            .expectError(ResponseStatusException.class)
            .verify();
    }

    @DisplayName("Delete User")
    @Test
    public void deleteUser() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(randomUser));

        BDDMockito.when(repository.delete(ArgumentMatchers.any(User.class)))
            .thenReturn(Mono.empty());

        StepVerifier
            .create(service.delete(randomUser.getId()))
            .expectSubscription()
            .verifyComplete();
    }
}
