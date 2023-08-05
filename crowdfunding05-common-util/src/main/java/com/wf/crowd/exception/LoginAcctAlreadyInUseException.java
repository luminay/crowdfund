package com.wf.crowd.exception;

/**
 * ClassName: LoginAcctAlreadyInUseException
 * Package: com.wf.crowd.exception
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/11 17:29
 * @Version 1.0
 */
public class LoginAcctAlreadyInUseException extends RuntimeException{
    public LoginAcctAlreadyInUseException() {
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
