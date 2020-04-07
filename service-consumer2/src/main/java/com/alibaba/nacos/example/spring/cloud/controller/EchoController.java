package com.alibaba.nacos.example.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
class EchoController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/echo")
    public String service( @RequestParam String string) {
        return restTemplate.getForObject("http://service-provider1/echo/" + string, String.class);
    }
}