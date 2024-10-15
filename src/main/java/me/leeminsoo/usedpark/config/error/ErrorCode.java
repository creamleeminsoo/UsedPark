package me.leeminsoo.usedpark.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"E1","올바르지 않은 입력값입니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,"E2","잘못된 HTTP 메서드를 호출했습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"E3","서버 에러가 발생했습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"E4","존재하지 않는 엔티티입니다"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "E5", "잘못된 인수입니다."),
    USER_NOT_AUTHENTICATION(HttpStatus.FORBIDDEN,"E5","인증되지 않았습니다"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"A1","존재하지않는 글입니다"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"A2","존재하지않는 댓글입니다"),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"A3","존재하지않는 아이템입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"A4","존재하지않는 유저입니다"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,"A5","존재하지않는 게시판입니다"),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND,"A6","존재하지 않는 Cart입니다"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"A7","존재하지 않는 카테고리 입니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"A8","RefreshToken이 존재 하지않습니다"),
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND,"A9","존재 하지 않는 채팅방입니다"),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST,"B1","이미 존재하는 이메일입니다"),
    LIKE_DUPLICATION(HttpStatus.BAD_REQUEST,"B2","이미 좋아요를 눌렀습니다"),
    CART_DUPLICATION(HttpStatus.BAD_REQUEST,"B3","이미 장바구니에 상품을 담았습니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "B4", "유효하지 않는 토큰입니다");


    private final String message;
    private final String code;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status,final String code,final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
