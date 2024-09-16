package me.leeminsoo.usedpark.config.error.exception.InvalidInput;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class InvalidTokenException extends InvalidInputValueException{
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
