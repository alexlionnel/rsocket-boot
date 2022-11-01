package com.example.rsocket_boot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromSupplier(() -> repository.findByUsername(username));
    }
}
