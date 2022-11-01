package com.example.rsocket_boot;

import com.example.rsocket_boot.dto.ComputationRequestDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec02DestinationVariableTest {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup() {
        this.requester = this.builder.transport(TcpClientTransport.create("localhost", 6565));
    }

    @Test
    void fireAndForget() {
        Mono<Void> mono = this.requester.route("math.service.print.55").send();

        StepVerifier.create(mono)
                .verifyComplete();
    }
}
