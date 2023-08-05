package com.wf.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Crowdfunding08MemberEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Crowdfunding08MemberEurekaApplication.class, args);
	}

}
