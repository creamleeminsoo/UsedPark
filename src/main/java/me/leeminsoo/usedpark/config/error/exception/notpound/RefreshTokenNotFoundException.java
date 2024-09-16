package me.leeminsoo.usedpark.config.error.exception.notpound;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class RefreshTokenNotFoundException extends NotFoundException{
    public RefreshTokenNotFoundException(){
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
