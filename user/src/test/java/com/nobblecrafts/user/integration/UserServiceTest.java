package com.nobblecrafts.user.integration;

import com.nobblecrafts.user.models.User;
import com.nobblecrafts.user.repository.UserRepository;
import com.nobblecrafts.user.services.UserService;
import com.nobblecrafts.user.utils.TestSupplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(UserService.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @MockBean
    private UserRepository repository;

    /**
     * WebClient for tests
     */
    @Autowired
    private WebTestClient client;

    private User randomUser;

    @BeforeEach
    public void setup() {
        randomUser = TestSupplier.createRandomValidUser();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(randomUser)); 
    }

    @Test
    public void singleEndpoint() {
        client.get()
            .uri("/users")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(randomUser.getId())
            .jsonPath("$.name").isEqualTo(randomUser.getName())
            .jsonPath("$.email").isEqualTo(randomUser.getEmail());
    }
}
