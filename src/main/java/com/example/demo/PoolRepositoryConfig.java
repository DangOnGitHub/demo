package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class PoolRepositoryConfig {
    @Bean
    public ConcurrentHashMap<Integer, Pool> pools() {
        return new ConcurrentHashMap<>();
    }
}
