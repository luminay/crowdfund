package com.wf.crowd.exception;

/**
 * ClassName: LoginFailedException
 * Package: com.wf.crowd.mvc.exception
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/10 11:42
 * @Version 1.0
 */
public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}