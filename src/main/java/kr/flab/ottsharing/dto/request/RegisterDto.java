package kr.flab.ottsharing.dto.request;

import lombok.Getter;

@Getter
public class RegisterDto {
    private String userId;
    private String userPassword;
    private String email;
}
