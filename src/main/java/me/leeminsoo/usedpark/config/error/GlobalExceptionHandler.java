package me.leeminsoo.usedpark.config.error;

import lombok.extern.slf4j.Slf4j;
import me.leeminsoo.usedpark.config.error.exception.BusinessBaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException e){
        log.warn("HttpRequestMethodNotSupportedException", e);
        return createErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(BusinessBaseException.class)
    protected ResponseEntity<ErrorResponse> handle(BusinessBaseException e) {
        log.warn("BusinessException",e); // 비즈니스 로직관련예외는 WARN레벨로 설정해서 유지보수성을 높힘
        return createErrorResponseEntity(e.getErrorCode());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handle(IllegalArgumentException e) {
        log.warn("IllegalArgumentException: {}", e.getMessage());
        return createErrorResponseEntity(ErrorCode.INVALID_ARGUMENT);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handle(Exception e){
        e.printStackTrace();
        log.error("Exception",e);
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode){
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus()
        );
    }
}
