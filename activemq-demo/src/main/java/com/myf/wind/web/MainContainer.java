package com.myf.wind.web;

import com.myf.wind.service.SendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MainContainer {

    @Resource
    private SendService sendService;

    @RequestMapping("send")
    public String send(){
        sendService.sendJms("springboot","LiLi");
        return "success";
    }
}
