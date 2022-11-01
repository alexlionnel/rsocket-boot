package com.example.rsocket_boot.client.serviceregistry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RSockeTServiceInstance {

    private String host;
    private int port;
}
