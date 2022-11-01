package com.example.rsocket_boot.controller;

import com.example.rsocket_boot.dto.ClientConnectionRequest;
import com.example.rsocket_boot.service.MathClientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ConnectionHandler {

    private final MathClientManager clientManager;

    /* @ConnectMapping
    public Mono<Void> handleConnection(ClientConnectionRequest request, RSocketRequester rSocketRequester) {
        System.out.println("connection setup : " + request);
        return request.getSecretKey().equals("password") ? Mono.empty() :
                Mono.fromRunnable(() -> rSocketRequester.rsocketClient().dispose());
    } */

    @ConnectMapping
    public Mono<Void> noEventConnection(RSocketRequester rSocketRequester) {
        System.out.println("no event connection setup");
        return Mono.empty();
    }

    @ConnectMapping("math.events.connection")
    public Mono<Void> mathEventConnection(RSocketRequester rSocketRequester) {
        System.out.println("math event connection setup");
        return Mono.fromRunnable(() -> clientManager.add(rSocketRequester));
    }
}
