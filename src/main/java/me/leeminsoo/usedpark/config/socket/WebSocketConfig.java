package me.leeminsoo.usedpark.config.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue"); // 클라이언트는 /queue 경로로 메시지를 구독 할 수 있음
        config.setApplicationDestinationPrefixes("/chat"); // 클라이언트가 서버로 메시지를 보낼 때 /chat 경로를 사용
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry config){
        config.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS(); // socket연결할 엔드포인트는 /ws-stomp, 버젼 낮은 브라우져를 위해 SockJS 라이브러리도 사용
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(stompHandler);
    }
}