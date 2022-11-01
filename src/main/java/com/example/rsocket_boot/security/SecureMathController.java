package com.example.rsocket_boot.security;

import com.example.rsocket_boot.dto.ChartResponseDto;
import com.example.rsocket_boot.dto.ComputationRequestDto;
import com.example.rsocket_boot.dto.ComputationResponseDto;
import com.example.rsocket_boot.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@MessageMapping("math.service.secured")
public class SecureMathController {

    private final MathService mathService;

    @PreAuthorize("hasRole('USER')")
    @MessageMapping("square")
    public Mono<ComputationResponseDto> findSquare(Mono<ComputationRequestDto> requestDtoMono, @AuthenticationPrincipal Mono<UserDetails> userDetailsMono) {
        userDetailsMono.doOnNext(System.out::println).subscribe();
        return mathService.findSquare(requestDtoMono);
    }

    @MessageMapping("table")
    public Flux<ComputationResponseDto> tableStream(Mono<ComputationRequestDto> requestDtoMono) {
        return requestDtoMono.flatMapMany(mathService::tableStream);
    }
}
