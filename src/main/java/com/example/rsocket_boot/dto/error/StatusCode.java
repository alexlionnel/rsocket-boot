package com.example.rsocket_boot.dto.error;

import lombok.Getter;

public enum StatusCode {
    EC001("given number not in range"),
    EC002("usage limit exceeded");

    @Getter
    private final String description;

    StatusCode(String description) {
        this.description = description;
    }
}


