package com.alibaba.nacos.example.spring.cloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "client_consumer1_sentinel", r -> r.path("/sentinel/qps").uri("http://localhost:9092")
                )
                .route(
                        "client_consumer1_sentinel_thread", r -> r.path("/sentinel/thread").uri("http://localhost:9092")
                )
                .build();
    }
}
