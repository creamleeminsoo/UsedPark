package me.leeminsoo.usedpark.config.error.exception.notpound;

import me.leeminsoo.usedpark.config.error.ErrorCode;

public class ChatRoomNotFoundException extends NotFoundException{
    public ChatRoomNotFoundException(){
        super(ErrorCode.CHAT_ROOM_NOT_FOUND);
    }
}
