package me.leeminsoo.usedpark.service.alarm;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.repository.alarm.EmitterRepository;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final UserRepository userRepository;
    private final EmitterRepository emitterRepository;
    private static final Long TIMEOUT = 600L * 1000 * 60; //600분

    public SseEmitter subscribe(Long userId){
        SseEmitter emitter = createEmitter(userId);
        sendToClient(userId,"userId :"+ userId ,"SSE 연결완료");

        return emitter;
    }

    public SseEmitter createEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitterRepository.save(userId, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteByUserId(userId));
        emitter.onTimeout(() -> emitterRepository.deleteByUserId(userId));

        return emitter;
    }

    public void sendToClient(Long userId, Object data, String comment) {
        SseEmitter emitter = emitterRepository.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(userId))
                        .name("sse")
                        .data(data)
                        .comment(comment));
            } catch (IOException e) {
                emitterRepository.deleteByUserId(userId);
                emitter.completeWithError(e);
            }
        }
    }

    public <T> void customAlarm(Long userId, T data, String comment, String type) { // 다른 클래스에서 사용하기 위해
        sendToClient(userId, data, comment, type);
    }

    private <T> void sendToClient(Long userId, T data, String comment, String type) {
        SseEmitter emitter = emitterRepository.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(userId))
                        .name(type)
                        .data(data)
                        .comment(comment));
            } catch (IOException e) {
                emitterRepository.deleteByUserId(userId);
                emitter.completeWithError(e);
            }
        }
    }




}
