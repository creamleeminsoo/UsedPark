package me.leeminsoo.usedpark.config.error.exception.notpound;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
