package com.alibaba.nacos.example.spring.cloud.controller;

import com.alibaba.nacos.example.spring.cloud.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/thread")
    public String thread() {
        return testService.thread();
    }

    @GetMapping("/qps")
    public String qps() {
        return testService.qps();
    }
}