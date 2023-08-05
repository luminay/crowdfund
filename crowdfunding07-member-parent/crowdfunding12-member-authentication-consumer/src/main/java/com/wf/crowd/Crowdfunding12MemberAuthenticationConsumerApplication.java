package com.wf.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@SpringBootApplication
@EnableRedisHttpSession
public class Crowdfunding12MemberAuthenticationConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Crowdfunding12MemberAuthenticationConsumerApplication.class, args);
    }

}
