package me.leeminsoo.usedpark.config.socket;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.InvalidTokenException;
import me.leeminsoo.usedpark.config.jwt.TokenProvider;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){ //Stomp 메세지 보낼때 인증
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand()== StompCommand.CONNECT){
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 부분을 제거
            }
            if (!tokenProvider.validToken(token)) {
                throw new InvalidTokenException();
            }
        }
        return message;
    }

}
