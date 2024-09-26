package me.leeminsoo.usedpark.repository.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository { // 메모리에 저장 추후 확장성을 위해 ex)Redis 사용위해 클래스로 repository 클래스로 사용 + (SRP)모듈화 증가
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>(); // 동시성을위해 ConcurrentHashMap 사용

    public void save(Long userId, SseEmitter emitter){
        emitters.put(userId,emitter);
    }
    public void deleteByUserId(Long userId){
        emitters.remove(userId);
    }
    public SseEmitter get(Long userId){
        return emitters.get(userId);
    }


}
