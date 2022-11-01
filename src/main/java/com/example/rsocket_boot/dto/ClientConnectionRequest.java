package com.example.rsocket_boot.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientConnectionRequest {

    private String clientId;
    private String secretKey;
}
