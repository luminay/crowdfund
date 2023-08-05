package com.wf.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wf.crowd.mapper")
@SpringBootApplication
public class Crowdfunding10MemberMysqlProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(Crowdfunding10MemberMysqlProviderApplication.class, args);
    }

}
