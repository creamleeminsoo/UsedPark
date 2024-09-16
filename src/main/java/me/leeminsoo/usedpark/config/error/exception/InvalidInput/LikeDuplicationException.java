package me.leeminsoo.usedpark.config.error.exception.InvalidInput;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class LikeDuplicationException extends InvalidInputValueException {
    public LikeDuplicationException(){
        super(ErrorCode.LIKE_DUPLICATION);
    }
}
