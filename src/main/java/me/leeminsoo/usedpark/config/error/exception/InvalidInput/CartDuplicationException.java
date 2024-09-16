package me.leeminsoo.usedpark.config.error.exception.InvalidInput;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class CartDuplicationException extends InvalidInputValueException {
    public CartDuplicationException(){
        super(ErrorCode.CART_DUPLICATION);
    }
}
