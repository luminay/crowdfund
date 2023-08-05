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
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException{
    public LoginAcctAlreadyInUseForUpdateException() {
    }

    public LoginAcctAlreadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
