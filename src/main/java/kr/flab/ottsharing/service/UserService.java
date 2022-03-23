package kr.flab.ottsharing.service;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.MyInfo;
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

    // User Entity 구조 변경으로 인해 동작하지 않는 코드
    public User enrollUser(String loginId){
        /*
        User user = User.builder()
            .userId(loginId)
            .build();

        User savedUser = userRepository.save(user);
        return savedUser;
        */
        return null;
    }

    // login이 된 자신의 아이디만 넣어 호출한다는 전제하에 작성하였음
    // 추후 login 구현됐을때 현재 로그인 된 아이디로 호출하게끔 수정 필요
    public MyInfo fetchMyInfo(String userId) {
        User user = userRepository.findByUserId(userId);
        return new MyInfo(userId, user.getEmail(), user.getMoney());
    }
}
