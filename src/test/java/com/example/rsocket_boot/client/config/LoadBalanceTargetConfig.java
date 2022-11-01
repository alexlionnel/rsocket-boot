package com.example.rsocket_boot.client.config;

import com.example.rsocket_boot.client.serviceregistry.RSockeTServiceInstance;
import com.example.rsocket_boot.client.serviceregistry.ServiceRegistryClient;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class LoadBalanceTargetConfig {

    private final ServiceRegistryClient registryClient;

    @Bean
    public Flux<List<LoadbalanceTarget>> targetsFlux() {
        return Flux.from(targets());
    }

    private Mono<List<LoadbalanceTarget>> targets() {
        return Mono.fromSupplier(() ->
            this.registryClient.getInstances().stream()
                    .map(server -> LoadbalanceTarget.from(key(server), transport(server)))
                    .collect(Collectors.toList()));
    }

    private String key(RSockeTServiceInstance instance) {
        return instance.getHost() + instance.getPort();
    }

    private ClientTransport transport(RSockeTServiceInstance instance) {
        return TcpClientTransport.create(instance.getHost(), instance.getPort());
    }
}
