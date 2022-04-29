package kr.flab.ottsharing.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.dto.response.MyInfo;
import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final Pattern VALID_EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    private final int PASSWORD_MIN_LENGTH = 8;

    public CommonResponse register(String userId, String userPassword, String email) {
        if (userRepository.existsByUserId(userId)) {
            return new CommonResponse(ResultCode.DUPLICATED_USER_ID);
        }
        if (userRepository.existsByEmail(email)) {
            return new CommonResponse(ResultCode.DUPLICATED_EMAIL);
        }
        if (isWeekPassword(userPassword)) {
            return new CommonResponse(ResultCode.WEAK_PASSWORD);
        }
        if (!isValidEmail(email)) {
            return new CommonResponse(ResultCode.INVALID_EMAIL);
        }

        User user = User.builder()
            .userId(userId)
            .userPassword(userPassword)
            .email(email)
            .money(0L)
            .build();

        userRepository.save(user);
        return new CommonResponse();
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

    public User login(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    public MyInfo fetchMyInfo(String userId) {
        User user = userRepository.findByUserId(userId).get();
        return new MyInfo(userId, user.getEmail(), user.getMoney());
    }

    public CommonResponse updateMyInfo(String userId, String changedPassword, String changedEmail) {
        User user = userRepository.findByUserId(userId).get();
        String passwordBeforeChange = user.getUserPassword();
        String emailBeforeChange = user.getEmail();
        boolean emailProblem = false;
        boolean isPasswordChange = false;
        boolean isEmailChange = false;

        if (!passwordBeforeChange.equals(changedPassword)) {
            user.setUserPassword(changedPassword);
            userRepository.save(user);
            isPasswordChange = true;
        }

        if (!emailBeforeChange.equals(changedEmail)) {
            boolean exist = userRepository.existsByEmail(changedEmail);
            if (!exist) {
                user.setEmail(changedEmail);
                userRepository.save(user);
                isEmailChange = true;
            } else {
                emailProblem = true;
            }
        }

        if (emailProblem) {
            user.setUserPassword(passwordBeforeChange);
            userRepository.save(user);
            return new CommonResponse(ResultCode.DUPLICATED_EMAIL);
        }
        if (isPasswordChange || isEmailChange) {
            return new CommonResponse(ResultCode.CHANGE_COMPLETE);
        }
        return new CommonResponse(ResultCode.NOTHING_CHANGED);
    }
}
