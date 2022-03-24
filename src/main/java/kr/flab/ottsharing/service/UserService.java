package kr.flab.ottsharing.service;

import kr.flab.ottsharing.protocol.MyPageUpdateResult;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public MyPageUpdateResult updateMyInfo(String userId, String changedPassword, String changedEmail) {

        User user = userRepository.findByUserId(userId);
        String passwordBeforeChange = user.getUserPassword();
        String emailBeforeChange = user.getEmail();
        boolean emailProblem = false;
        boolean isPasswordChange = false;
        boolean isEmailChange = false;

        if(!passwordBeforeChange.equals(changedPassword)) {
            user.setUserPassword(changedPassword);
            userRepository.save(user);
            isPasswordChange = true;
        }

        if(!emailBeforeChange.equals(changedEmail)) {
            boolean exist = userRepository.existsByEmail(changedEmail);
            if (!exist) {
                user.setEmail(changedEmail);
                userRepository.save(user);
                isEmailChange = true;
            } else {
                emailProblem = true;
            }
        }

        MyPageUpdateResult result = MyPageUpdateResult.NOTHING_CHANGED;
        if(emailProblem) {
            user.setUserPassword(passwordBeforeChange);
            userRepository.save(user);
            result = MyPageUpdateResult.DUPLICATED_EMAIL;
        } else if(isPasswordChange || isEmailChange) {
            result = MyPageUpdateResult.CHANGE_COMPLETE;
        }
        return result;
    }
    
}
