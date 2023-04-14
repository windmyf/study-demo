package com.myf.wind.cloudsentinelservice9110.api;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA(){
        return "------testA------";
    }
    @GetMapping("/testB")
    public String testB(){
        return "------testB------";
    }
}
