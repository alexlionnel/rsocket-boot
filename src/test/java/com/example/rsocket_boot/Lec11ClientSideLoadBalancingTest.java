package com.example.rsocket_boot;

import com.example.rsocket_boot.dto.ComputationRequestDto;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.loadbalance.RoundRobinLoadbalanceStrategy;
import io.rsocket.loadbalance.WeightedLoadbalanceStrategy;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
})
public class Lec11ClientSideLoadBalancingTest {

    @Autowired
    private RSocketRequester.Builder builder;

    @Autowired
    private Flux<List<LoadbalanceTarget>> targets;

    @Test
    void clientSide() throws InterruptedException {
        RSocketRequester requester1 = this.builder
                .transports(targets, WeightedLoadbalanceStrategy.create());

        for (int i = 0; i < 50; i++) {
            requester1.route("math.service.print")
                    .data(new ComputationRequestDto(i))
                    .send()
                    .subscribe();

            Thread.sleep(10000);
        }


    }
}
