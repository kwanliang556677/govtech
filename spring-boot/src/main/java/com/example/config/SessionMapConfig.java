package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SessionMapConfig {
    @Bean(name = "sessionSet")
    public Set sessionMap() {
        return ConcurrentHashMap.newKeySet();
    }

    @Bean(name = "lunchLocationMap")
    public Map<String, Map<String, String>> lunchLocationMap() {
        return new ConcurrentHashMap();
    }

    @Bean(name = "selectedRestaurant")
    public Map<String, String> selectedRestaurantMap() {
        return new ConcurrentHashMap();
    }

    @Bean(name = "joinedSessionMap")
    public Map<String, String> joinedSessionMap() {
        return new ConcurrentHashMap();
    }
}
