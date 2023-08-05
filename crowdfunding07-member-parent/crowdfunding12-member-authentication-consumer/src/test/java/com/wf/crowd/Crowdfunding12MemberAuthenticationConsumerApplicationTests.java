package com.wf.crowd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Crowdfunding12MemberAuthenticationConsumerApplication.class)
class Crowdfunding12MemberAuthenticationConsumerApplicationTests {

    @Test
    void contextLoads() {
        sendEmail("3217113363@qq.com","测试","测试成功");
    }

    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public boolean sendEmail(String toEmail, String text, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置发件邮箱
        simpleMailMessage.setFrom(fromEmail);
        //收件人邮箱
        simpleMailMessage.setTo(toEmail);
        //主题标题
        simpleMailMessage.setSubject(text);
        //信息内容
        simpleMailMessage.setText(message);
        //执行发送
        try {//发送可能失败
            javaMailSender.send(simpleMailMessage);
            //没有异常返回true，表示发送成功
            System.out.println("..............");
            return true;
        } catch (Exception e) {
            //发送失败，返回false
            return false;
        }
    }
}
