package kr.flab.ottsharing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.flab.ottsharing.protocol.RegisterResult;
import kr.flab.ottsharing.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void register_save_잘됨() {
        // given
        given(userRepository.existsByUserId("userId")).willReturn(false);
        given(userRepository.existsByEmail("email@email.com")).willReturn(false);

        // when
        String userId = "userId";
        String userPassword = "userPassword12";
        String email = "email@email.com";
        RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(RegisterResult.SUCCESS, result);
    }

    @Test
    void register_중복_아이디는_에러() {
        // given
        given(userRepository.existsByUserId("userId")).willReturn(true);

        // when
        String userId = "userId";
        String userPassword = "userPassword12";
        String email = "email@email.com";
        RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(RegisterResult.DUPLICATE_USER_ID, result);
    }

    @Test
    void register_중복_이메일은_에러() {
        // given
        given(userRepository.existsByEmail("email@email.com")).willReturn(true);

        // when
        String userId = "userId";
        String userPassword = "userPassword12";
        String email = "email@email.com";
        RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(RegisterResult.DUPLICATE_EMAIL, result);
    }

    @Test
    void 유효하지_않는_메일() {
        // when
        String userId = "userId";
        String userPassword = "userPassword12";
        String email = "email";
        RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(RegisterResult.INVALID_EMAIL, result);
    }

    @Test
    void 취약한_비밀번호() {
        // when
        String userId = "userId";
        String userPassword = "userPassword";
        String email = "email";
        RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(RegisterResult.WEAK_PASSWORD, result);
    }
}
