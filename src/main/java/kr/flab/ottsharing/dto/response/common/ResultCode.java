package kr.flab.ottsharing.dto.response.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("성공"), CHANGE_COMPLETE("변경 완료"), NOTHING_CHANGED("아무것도 변경되지 않음"), ON_WAITING("파티 가입 대기중"),

    DUPLICATED_USER_ID("중복된 아이디"), DUPLICATED_EMAIL("중복된 이메일"),
    INVALID_EMAIL("유효하지 않은 이메일"), WEAK_PASSWORD("취약한 비밀번호"),
    HAS_MONEY("돈이 남아있음"), NOT_ENOUGH_MONEY("돈이 부족함"),
    HAS_PARTY("가입된 파티 있음"), HAS_NO_PARTY("가입된 파티 없음"),
    DUPLICATED_NICKNAME("중복된 닉네임"), LEADER_ONLY("파티장만 할 수 있음"),
    LOGIN_FAILED("로그인 실패"), NOT_EXIST_USER("존재하지 않는 사용자"),
    PARTY_MEMBER_ONLY("파티원만 할 수 있음"), IN_PARTY_ONLY("파티에 가입된 사람만 할 수 있음");
    
    private final String message;

    private ResultCode(String message) {
        this.message = message;
    }
}
