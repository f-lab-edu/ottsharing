package kr.flab.ottsharing.service;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.UserDeleteResult;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PartyMemberRepository partyMemberRepository;


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

    // userId는 로그인 된 아이디를 컨트롤러에서 넘겨준다고 전제함
    // JWT /login이 구현되면 추후 수정
    public UserDeleteResult deleteMyInfo(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user.getMoney() > 0) {
            return UserDeleteResult.HAS_MONEY;
        }
        if (partyMemberRepository.findOneByUser(user).isPresent()) {
            return UserDeleteResult.HAS_PARTY;
        }

        userRepository.deleteById(user.getId());
        return UserDeleteResult.SUCCESS;
    }
}
