package com.alibaba.nacos.example.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class ServiceConsumer2 {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumer2.class, args);
    }
}


