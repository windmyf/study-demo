package com.myf.wind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudNacos9002Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudNacos9002Application.class, args);
    }

}
