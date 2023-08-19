package com.wf.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@SpringBootApplication
@EnableRedisHttpSession
@EnableConfigurationProperties
public class Crowdfunding13MemberProjectConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Crowdfunding13MemberProjectConsumerApplication.class, args);
	}

}
