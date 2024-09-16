package me.leeminsoo.usedpark.config.error.exception.notpound;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(){
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
