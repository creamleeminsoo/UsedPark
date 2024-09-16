package me.leeminsoo.usedpark.config.error.exception.InvalidInput;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class EmailDuplicationException extends InvalidInputValueException {
    public EmailDuplicationException(){
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
