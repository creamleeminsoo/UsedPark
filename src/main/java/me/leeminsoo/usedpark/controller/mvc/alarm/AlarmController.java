package me.leeminsoo.usedpark.controller.mvc.alarm;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.jwt.TokenProvider;
import me.leeminsoo.usedpark.service.alarm.AlarmService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final TokenProvider tokenProvider;

    @GetMapping(value = "/subscribe/{accessToken}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable(name = "accessToken") String accessToken){ // SSE 메세지는 SseEmitter 객체로 리턴
        Long userId = tokenProvider.getUserId(accessToken);
        return alarmService.subscribe(userId);
    }

}
