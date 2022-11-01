package com.example.rsocket_boot;

import com.example.rsocket_boot.dto.ClientConnectionRequest;
import com.example.rsocket_boot.dto.ComputationRequestDto;
import com.example.rsocket_boot.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
})
public class Lec07ConnectionManagerTest {


    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    void connectionTest() throws InterruptedException {
        RSocketRequester requester1 = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));

        RSocketRequester requester2 = this.builder
                .setupRoute("math.events.connection")
                .transport(TcpClientTransport.create("localhost", 6565));

        requester1.route("math.service.print")
                .data(new ComputationRequestDto(5))
                .send()
                .subscribe();
        requester2.route("math.service.print")
                .data(new ComputationRequestDto(5))
                .send()
                .subscribe();

        Thread.sleep(10000);
    }
}
