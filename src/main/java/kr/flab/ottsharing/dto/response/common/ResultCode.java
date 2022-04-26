package kr.flab.ottsharing.dto.response.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("성공"), CHANGE_COMPLETE("변경 완료"), NOTHING_CHANGED("아무것도 변경되지 않음"), ON_WAITING("파티 가입 대기중"),

    DUPLICATED_USER_ID("중복된 아이디"), DUPLICATED_EMAIL("중복된 이메일"), INVALID_EMAIL("유효하지 않은 이메일"),
    WEAK_PASSWORD("취약한 비밀번호"), HAS_MONEY("돈이 남아있음"), HAS_PARTY("가입된 파티 있음"), HAS_NO_PARTY("가입된 파티 없음");
    
    private final String message;

    private ResultCode(String message) {
        this.message = message;
    }
}
