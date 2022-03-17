package kr.flab.ottsharing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private final UserRepository userRepository;


    public User loginCheck(String loginId) {
        Optional<User> user = userRepository.findById(loginId);
        if(user.isPresent()){
            return user.get();
        }

        return null;
    }

    public User enrollUser(String loginId){
        User user = User.builder()
            .userId(loginId)
            .build();

        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
