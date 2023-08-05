package com.wf.crowd.service;

/**
 * ClassName: EmailService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/23 19:16
 * @Version 1.0
 */
public interface EmailService {
    /**
     * toEmail 接收验证码的邮箱
     * text 主题
     * message 主体信息
     * */
    public boolean sendEmail(String toEmail, String text, String message);
}

