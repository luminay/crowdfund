package com.wf.crowd.mvc.config;

import com.wf.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: SecurityConfig
 * Package: com.wf.crowd.mvc.config
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/18 14:53
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
// 启用全局方法权限控制功能，并且设置 prePostEnabled = true。保证@PreAuthority、
//@PostAuthority、@PreFilter、@PostFilter 生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CrowdUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        //builder.inMemoryAuthentication().withUser("tom").password("123123").roles("ADMIN");
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers("/admin/do/login/page.html","/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**"," /layer/**","/script/**","/ztree/**","/crowd/**")
                .permitAll()
                .antMatchers("/admin/do/main/user.html")
                .access("hasRole('经理') or hasAuthority('user:get')")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/admin/error")
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/admin/do/login/page.html")
                .loginProcessingUrl("/security/admin/do/login.html")
                .defaultSuccessUrl("/admin/do/main/page.html")
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .and()
                .logout()
                .logoutUrl("/security/admin/do/logout.html")
                .logoutSuccessUrl("/admin/do/login/page.html");
    }
}
