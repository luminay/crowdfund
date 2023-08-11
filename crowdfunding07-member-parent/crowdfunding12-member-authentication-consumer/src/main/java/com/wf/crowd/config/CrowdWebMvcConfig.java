package com.wf.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: CrowdWebMvcConfig
 * Package: com.wf.crowd.config
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/24 10:44
 * @Version 1.0
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/member/to/reg/page").setViewName("member-reg");
        registry.addViewController("/member/to/login/page").setViewName("member-login");
        registry.addViewController("/member/to/mycrowd").setViewName("member-crowd");
    }
}
