package kr.flab.ottsharing.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.flab.ottsharing.entity.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 내용넣기테스트(){
        User user1 = User.builder()
            .userId("유저1")
            .build();

        User user2 = User.builder()
            .userId("유저2")
            .build();

        User user3 = User.builder()
            .userId("유저3")
            .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }


}
