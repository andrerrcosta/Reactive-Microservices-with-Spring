package com.nobblecrafts.zsystemtester.services;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SystemTesterWebSocketHandler implements SystemTesterCommunication {

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        final List<String> msg = List.of("Maybe later...", "for more of my works access www.nobblecrafts.com", "End Of Test");
        final Flux<String> zip = Flux.interval(Duration.ofMillis(1000L))
            .zipWithIterable(msg)
            .map(source -> source.getT1() + ": " + source.getT2());

        return  session.send(zip.take(3).map(session::textMessage))
            .and(session.receive().map(WebSocketMessage::getPayloadAsText))
            .log();
    }

}
