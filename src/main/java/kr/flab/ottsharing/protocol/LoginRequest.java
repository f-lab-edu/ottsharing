package kr.flab.ottsharing.protocol;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class LoginRequest {

    private String userId;
    private String password;


    public UsernamePasswordAuthenticationToken toAuthentication() {

        return new UsernamePasswordAuthenticationToken(userId, password);
    }

}

