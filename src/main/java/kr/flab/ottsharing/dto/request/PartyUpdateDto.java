package kr.flab.ottsharing.dto.request;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class PartyUpdateDto {
    @Nullable
    private String ottId;

    @Nullable
    private String ottPassword;

    @Nullable
    private String nicknameToChange;

    public PartyUpdateDto(String ottId, String ottPassword, String nicknameToChange) { 
        this.ottId = ottId;
        this.ottPassword = ottPassword;
        this.nicknameToChange = nicknameToChange;
    }

    public boolean checkAllBlank() {
        if (ottId == null && ottPassword == null && nicknameToChange == null ) {
            return true;
        }
        return false;
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
