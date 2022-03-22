package kr.flab.ottsharing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        given(userRepository.existsByEmail("email")).willReturn(false);

        // when
        String userId = "userId";
        String userPassword = "userPassword";
        String email = "email";
        UserService.RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(UserService.RegisterResult.SUCCESS, result);
    }

    @Test
    void register_중복_아이디는_에러() {
        // given
        given(userRepository.existsByUserId("userId")).willReturn(true);

        // when
        String userId = "userId";
        String userPassword = "userPassword";
        String email = "email";
        UserService.RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(UserService.RegisterResult.DUPLICATE_USER_ID, result);
    }

    @Test
    void register_중복_이메일은_에러() {
        // given
        given(userRepository.existsByEmail("email")).willReturn(true);

        // when
        String userId = "userId";
        String userPassword = "userPassword";
        String email = "email";
        UserService.RegisterResult result = userService.register(userId, userPassword, email);

        // then
        assertEquals(UserService.RegisterResult.DUPLICATE_EMAIL, result);
    }
}
