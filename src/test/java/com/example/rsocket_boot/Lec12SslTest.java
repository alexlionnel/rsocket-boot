package com.example.rsocket_boot;

import com.example.rsocket_boot.dto.ComputationRequestDto;
import com.example.rsocket_boot.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.test.StepVerifier;

@SpringBootTest
public class Lec12SslTest {

    static {
        System.setProperty("javax.net.ssl.trustStore", "/Users/97023r/IdeaProjects/rsocket-boot/ssl-tls/client.truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
    }

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    void sslTest() {
        RSocketRequester requester = this.builder
                .transport(TcpClientTransport.create(
                        TcpClient.create().host("localhost").port(6565).secure()
                ));

            Mono<ComputationResponseDto> mono = requester.route("math.service.square")
                    .data(new ComputationRequestDto(5))
                    .retrieveMono(ComputationResponseDto.class)
                    .doOnNext(System.out::println);

            StepVerifier.create(mono)
                    .expectNextCount(1)
                    .verifyComplete();
    }
}
