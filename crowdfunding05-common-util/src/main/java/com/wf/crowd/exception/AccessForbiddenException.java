package com.wf.crowd.exception;

/**
 * ClassName: AccessForbiddenException
 * Package: com.wf.crowd.mvc.exception
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/10 15:46
 * @Version 1.0
 */
public class AccessForbiddenException extends RuntimeException{

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
