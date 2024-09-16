package me.leeminsoo.usedpark.config.error.exception.notpound;


import me.leeminsoo.usedpark.config.error.ErrorCode;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
