package com.wf.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@EnableRedisHttpSession
@SpringBootApplication
public class Crowdfunding15MemberPayConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Crowdfunding15MemberPayConsumerApplication.class, args);
	}

}
