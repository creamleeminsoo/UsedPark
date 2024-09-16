package me.leeminsoo.usedpark.config.error.exception.notpound;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class BoardNotFoundException extends NotFoundException {
    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
