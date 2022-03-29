package kr.flab.ottsharing.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Pattern VALID_EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    private final int PASSWORD_MIN_LENGTH = 8;

    public RegisterResult register(String userId, String userPassword, String email){
        if (userRepository.existsByUserId(userId)) {
            return RegisterResult.DUPLICATE_USER_ID;
        }
        if (userRepository.existsByEmail(email)) {
            return RegisterResult.DUPLICATE_EMAIL;
        }
        if (isWeekPassword(userPassword)) {
            return RegisterResult.WEAK_PASSWORD;
        }
        if (!isValidEmail(email)) {
            return RegisterResult.INVALID_EMAIL;
        }

        User user = User.builder()
            .userId(userId)
            .userPassword(userPassword)
            .email(email)
            .build();

        userRepository.save(user);
        return RegisterResult.SUCCESS;
    }

    private boolean isWeekPassword(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            return true;
        }
        if (!includesBothNumberAndAlphabet(password)) {
            return true;
        }
        return false;
    }

    private boolean includesBothNumberAndAlphabet(String str) {
        boolean includesNumber = false;
        boolean includesAlphabet = false;

        for (char ch : str.toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                includesAlphabet = true;
            }
            if (Character.isDigit(ch)) {
                includesNumber = true;
            }
        }

        return includesAlphabet && includesNumber;
    }

    private boolean isValidEmail(String email) {
        return VALID_EMAIL_PATTERN.matcher(email).matches();
    }

    public enum RegisterResult {
        DUPLICATE_USER_ID, DUPLICATE_EMAIL, INVALID_EMAIL, WEAK_PASSWORD, SUCCESS
    }
}