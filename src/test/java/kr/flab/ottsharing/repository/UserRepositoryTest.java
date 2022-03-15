package kr.flab.ottsharing.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.flab.ottsharing.entity.User;

@SpringBootTest
@Transactional
// @RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 내용넣기테스트(){
        User user1 = User.builder()
            .userId("유저1")
            .build();

        userRepository.save(user1);

        User result = userRepository.findById(user1.getUserId()).get();
        Assertions.assertThat(user1).isEqualTo(result);
   }


}
