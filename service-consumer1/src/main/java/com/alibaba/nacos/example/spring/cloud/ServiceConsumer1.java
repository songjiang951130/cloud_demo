package com.alibaba.nacos.example.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class ServiceConsumer1 {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumer1.class, args);
    }
}


