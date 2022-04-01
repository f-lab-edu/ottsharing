package kr.flab.ottsharing.protocol;

import kr.flab.ottsharing.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String email;

    public static LoginResponse of(User user) {
        return new LoginResponse(user.getEmail());
    }

}
