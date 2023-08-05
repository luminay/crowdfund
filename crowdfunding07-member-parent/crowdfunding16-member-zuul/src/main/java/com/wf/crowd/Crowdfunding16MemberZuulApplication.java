package com.wf.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Crowdfunding16MemberZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(Crowdfunding16MemberZuulApplication.class, args);
	}

}
