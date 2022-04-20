package kr.flab.ottsharing.protocol;

import javax.validation.constraints.NotBlank;

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

    @NotBlank(message = "그룹Id 정보가 반드시 필요합니다.")
    private Integer partyId;

}
