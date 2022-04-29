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
        return ottId == null && ottPassword == null && nicknameToChange == null;
    }

    public boolean existId() {
        return ottId != null;
    }

    public boolean existPassword() {
        return ottPassword != null;
    }

    public boolean existNickName() {
        return nicknameToChange != null;
    }

    public boolean existIdOrPassword() {
        return ottId != null || ottPassword != null;
    }
}
