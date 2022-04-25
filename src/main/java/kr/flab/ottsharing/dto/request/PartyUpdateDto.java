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
}
