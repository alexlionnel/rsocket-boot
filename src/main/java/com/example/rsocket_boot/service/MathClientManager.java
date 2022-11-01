package com.example.rsocket_boot.service;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class MathClientManager {

    private final Set<RSocketRequester> set = Collections.synchronizedSet(new HashSet<>());

    public void add(RSocketRequester rSocketRequester) {
        rSocketRequester.rsocket()
                .onClose()
                .doFirst(() -> this.set.add(rSocketRequester))
                .doFinally(s -> {
                    System.out.println("Finally");
                    this.set.remove(rSocketRequester);
                }).subscribe();
    }

    @Scheduled(fixedRate = 1000)
    public void notify(int i) {
        Flux.fromIterable(set)
                        .flatMap(r -> r.route("product.updated.event").data(i).send())
                                .subscribe();
        System.out.println(set);
    }

}

