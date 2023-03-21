package com.myf.wind.nacos.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${server.port}")
    private String severPort;

    @GetMapping(value = "/windmyf")
    public String getSeverPort(){
        return "nacos port : " + severPort;
    }
}
