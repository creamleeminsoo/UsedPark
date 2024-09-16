package me.leeminsoo.usedpark.config.error.exception;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class UserNotAuthenticationException extends BusinessBaseException{
    public UserNotAuthenticationException() {
        super(ErrorCode.USER_NOT_AUTHENTICATION);
    }
}
