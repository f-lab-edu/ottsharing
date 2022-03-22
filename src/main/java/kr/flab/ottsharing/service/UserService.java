package kr.flab.ottsharing.service;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public RegisterResult register(String userId, String userPassword, String email){
        if (userRepository.existsByUserId(userId)) {
            return RegisterResult.DUPLICATE_USER_ID;
        }
        if (userRepository.existsByEmail(email)) {
            return RegisterResult.DUPLICATE_EMAIL;
        }

        User user = User.builder()
            .userId(userId)
            .userPassword(userPassword)
            .email(email)
            .build();

        userRepository.save(user);
        return RegisterResult.SUCCESS;
    }

    public enum RegisterResult {
        DUPLICATE_USER_ID, DUPLICATE_EMAIL, SUCCESS
    }
}