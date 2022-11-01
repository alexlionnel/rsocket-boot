package com.example.rsocket_boot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final PasswordEncoder encoder;

    private Map<String, UserDetails> db;

    @PostConstruct
    private void init() {
        this.db = Map.of(
                "user", User.withUsername("user").password(encoder.encode("password")).roles("USER").build(),
                "admin", User.withUsername("admin").password(encoder.encode("password")).roles("ADMIN").build(),
                "client", User.withUsername("client").password(encoder.encode("password")).roles("TRUSTED_CLIENT").build()
        );
    }

    public UserDetails findByUsername(String username) {
        return this.db.get(username);
    }
}
