package kr.flab.ottsharing.service;

import kr.flab.ottsharing.protocol.MyPageUpdateResult;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.UserDeleteResult;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.protocol.MyInfo;
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
  
    // login이 된 자신의 아이디만 넣어 호출한다는 전제하에 작성하였음
    // 추후 login 구현됐을때 현재 로그인 된 아이디로 호출하게끔 수정 필요
    public MyInfo fetchMyInfo(String userId) {
        User user = userRepository.findByUserId(userId);
        return new MyInfo(userId, user.getEmail(), user.getMoney());
    }

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
