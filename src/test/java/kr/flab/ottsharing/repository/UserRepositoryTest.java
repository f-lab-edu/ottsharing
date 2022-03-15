package kr.flab.ottsharing.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.flab.ottsharing.entity.User;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 내용넣기테스트(){
        User user1 = User.builder()
            .userId("유저1")
            .createdTime(LocalDateTime.now())
            .build();

        User user2 = User.builder()
            .userId("유저2")
            .createdTime(LocalDateTime.now())
            .build();

        User user3 = User.builder()
            .userId("유저3")
            .createdTime(LocalDateTime.now())
            .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }


}
