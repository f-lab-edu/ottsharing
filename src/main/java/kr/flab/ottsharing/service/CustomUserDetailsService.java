package kr.flab.ottsharing.service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.CustomUser;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User principal = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));

        // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
        return new CustomUser(principal);
    }


}
