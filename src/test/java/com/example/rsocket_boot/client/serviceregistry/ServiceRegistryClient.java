package com.example.rsocket_boot.client.serviceregistry;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRegistryClient {
    private final List<RSockeTServiceInstance> instances;

    public ServiceRegistryClient() {
        this.instances = List.of(
                new RSockeTServiceInstance("localhost", 7070),
                new RSockeTServiceInstance("localhost", 7071),
                new RSockeTServiceInstance("localhost", 7072)
        );
    }

    public List<RSockeTServiceInstance> getInstances() {
        return instances;
    }
}
