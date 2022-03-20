package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.flab.ottsharing.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user1;

    @BeforeEach
    public void before(){
        user1 = User.builder()
                .userId("유저1")
                .userPassword("1234")
                .email("email1")
                .money(0l)
                .build();

        userRepository.save(user1);
    }
    @Test
    void 가입_테스트(){

        User result = userRepository.findByUserId(user1.getUserId());
        assertEquals("유저1", result.getUserId());
        assertEquals("1234", result.getUserPassword());
        assertEquals("email1", result.getEmail());

   }

    @Test
    void 탈퇴_테스트() {

        userRepository.deleteById(user1.getId());
        boolean result = userRepository.existsByUserId(user1.getUserId());
        assertEquals(false, result);

    }
}
