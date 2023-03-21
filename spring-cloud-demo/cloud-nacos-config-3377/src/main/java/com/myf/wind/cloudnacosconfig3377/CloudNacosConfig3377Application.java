package com.myf.wind.cloudnacosconfig3377;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
class CloudNacosConfig3377Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudNacosConfig3377Application.class, args);
    }

}
