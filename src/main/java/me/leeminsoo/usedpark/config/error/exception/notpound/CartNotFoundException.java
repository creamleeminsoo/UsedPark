package me.leeminsoo.usedpark.config.error.exception.notpound;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class CartNotFoundException extends NotFoundException {
    public CartNotFoundException() {
        super(ErrorCode.CART_NOT_FOUND);
    }
}
