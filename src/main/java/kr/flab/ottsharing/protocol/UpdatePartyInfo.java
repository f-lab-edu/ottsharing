package kr.flab.ottsharing.protocol;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class UpdatePartyInfo {
    @Nullable
    private String ottId;

    @Nullable
    private String ottPassword;

    @Nullable
    private String nicknameToChange;
}
