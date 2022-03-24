package kr.flab.ottsharing.service;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    // User Repository 구조 변경으로 인해 동작하지 않는 코드
    public User loginCheck(String loginId) {
        /*
        Optional<User> user = userRepository.findById(loginId);
        if(user.isPresent()){
            return user.get();
        }*/

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
