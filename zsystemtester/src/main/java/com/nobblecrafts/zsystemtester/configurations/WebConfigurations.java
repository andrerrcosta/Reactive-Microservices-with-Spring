package com.nobblecrafts.zsystemtester.configurations;

import java.util.Map;

import com.nobblecrafts.zsystemtester.api.SystemTester;
import com.nobblecrafts.zsystemtester.models.TestReport;
import com.nobblecrafts.zsystemtester.services.SystemTesterWebSocketHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFlux
public class WebConfigurations implements WebFluxConfigurer {

    @Autowired
    private SystemTesterWebSocketHandler handler;

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        return new SimpleUrlHandlerMapping(Map.of("/systemtester/dispatcher/", handler), Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    // @Override
    // public void addCorsMappings(CorsRegistry corsRegistry) {
    //     corsRegistry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("PUT", "POST", "DELETE",
    //             "GET");
    // }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8083")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Bean
    public SystemTester systemTester() {
        return new SystemTester() {

            @Override
            public <T extends TestReport> Mono<Void> observe(T object) {
                return Mono.empty();
            }

            @Override
            public <T extends TestReport> Mono<Void> observe(Mono<T> object) {
                return Mono.empty();
            }

            @Override
            public <T extends TestReport> Mono<Void> observe(Flux<T> object) {
                return Mono.empty();
            }

            @Override
            public SystemTester connect() {
                return this;
            }
            
        };
    }

}
