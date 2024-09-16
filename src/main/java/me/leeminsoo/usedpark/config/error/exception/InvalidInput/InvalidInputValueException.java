package me.leeminsoo.usedpark.config.error.exception.InvalidInput;

import me.leeminsoo.usedpark.config.error.ErrorCode;
import me.leeminsoo.usedpark.config.error.exception.BusinessBaseException;

public class InvalidInputValueException extends BusinessBaseException {
    public InvalidInputValueException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }
    public InvalidInputValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
