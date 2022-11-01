package com.example.rsocket_boot;

import com.example.rsocket_boot.dto.ComputationRequestDto;
import com.example.rsocket_boot.dto.ComputationResponseDto;
import io.rsocket.core.Resume;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
})
public class Lec09SessionResumptionTest {

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    void connectionTest() {
        RSocketRequester requester = this.builder
                .rsocketConnector(c -> c
                        .resume(resumeStrategy())
                        .reconnect(retryStrategy()))
                .transport(TcpClientTransport.create("192.168.59.103", 6566)); // minikube ip + nginx port

        Flux<ComputationRequestDto> flux = requester.route("math.service.table")
                .data(new ComputationRequestDto(5))
                .retrieveFlux(ComputationRequestDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(1000)
                .verifyComplete();
    }

    private Retry retryStrategy() {
        return Retry.fixedDelay(10, Duration.ofSeconds(2))
                .doBeforeRetry(s -> System.out.println("retrying " + s.totalRetriesInARow()));
    }

    private Resume resumeStrategy() {
        return new Resume()
                .retry(Retry.fixedDelay(2000, Duration.ofSeconds(2))
                        .doBeforeRetry(s -> System.out.println("resume - retrying " + s.totalRetriesInARow())));
    }
}
