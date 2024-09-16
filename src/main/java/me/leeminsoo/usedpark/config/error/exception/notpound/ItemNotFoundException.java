package me.leeminsoo.usedpark.config.error.exception.notpound;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException() {
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
