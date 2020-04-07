package com.alibaba.nacos.example.spring.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class EchoController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/echo")
    public String service(@RequestParam String string) {
        System.out.println("visit");
        return restTemplate.getForObject("http://service-provider1/echo/" + string, String.class);
    }

    @RequestMapping("/prod")
    public String serviceProd(@RequestParam String string) {
        if (string.equals("error")) {
            throw new RuntimeException("run error");
        }
        System.out.println("visit");
        return restTemplate.getForObject("http://service-provider1/echo/" + string, String.class);
    }

    /**
     * 定义sentinel资源
     * @param string
     * @return
     */
    @SentinelResource(value = "echoController", blockHandler = "error")
    @RequestMapping("/error_msg")
    public String serviceError(@RequestParam String string) {
            throw new RuntimeException("run error");
    }

    public String error(String id, BlockException ex) {
        return "系统异常:" + ex.getMessage();
    }
}