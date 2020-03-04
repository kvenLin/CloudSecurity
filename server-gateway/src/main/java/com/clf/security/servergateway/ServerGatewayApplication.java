package com.clf.security.servergateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ServerGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerGatewayApplication.class, args);
    }

}
