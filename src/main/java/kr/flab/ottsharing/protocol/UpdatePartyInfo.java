package kr.flab.ottsharing.protocol;

import org.springframework.lang.Nullable;

import kr.flab.ottsharing.exception.WrongInfoException;
import lombok.Getter;

@Getter
public class UpdatePartyInfo {
    @Nullable
    private String ottId;

    @Nullable
    private String ottPassword;

    @Nullable
    private String nicknameToChange;

    public UpdatePartyInfo(String ottId, String ottPassword, String nicknameToChange) { 
        this.ottId = ottId;
        this.ottPassword = ottPassword;
        this.nicknameToChange = nicknameToChange;
        checkAllNull();
    }

    public void checkAllNull() {
        if (ottId == null && ottPassword == null && nicknameToChange == null ) {
            throw new WrongInfoException("바꿀 정보가 아무것도 입력되지 않았습니다.");
        }
    }

    public boolean existId() {
        if (ottId == null) {
            return false;
        }
        return true;
    }

    public boolean existPassword() {
        if (ottPassword == null) {
            return false;
        }
        return true;
    }

    public boolean existNickName() {
        if (nicknameToChange == null) {
            return false;
        }
        return true;
    }

    public boolean existIdOrPassword() {
        if (ottId != null || ottPassword != null) {
            return true;
        }
        return false;
    }
}
