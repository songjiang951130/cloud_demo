package com.alibaba.nacos.example.spring.cloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @SentinelResource(value = "thread", blockHandler = "error")
    public String thread()  {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "thread";
    }

    @SentinelResource(value = "qps", blockHandler = "error")
    public String qps() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "qps";
    }

    public String error(String id, BlockException ex) {
        return "系统异常:" + ex.getMessage();
    }

}
