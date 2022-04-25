package kr.flab.ottsharing.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateDto {
    private String password;
    private String email;
}
