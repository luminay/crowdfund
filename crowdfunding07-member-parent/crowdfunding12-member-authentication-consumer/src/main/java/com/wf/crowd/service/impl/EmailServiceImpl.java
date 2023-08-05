package com.wf.crowd.service.impl;

import com.wf.crowd.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName: EmailServiceImpl
 * Package: com.wf.crowd.service.impl
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/23 19:17
 * @Version 1.0
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
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
            return true;
        } catch (Exception e) {
            //发送失败，返回false
            return false;
        }
    }
}
