package kr.flab.ottsharing.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.flab.ottsharing.entity.PartyWaiting;
import kr.flab.ottsharing.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
public class PartyWaitingRepositoryTest {
    @Autowired
    private PartyWaitingRepository waitRepo;
    @Autowired
    private UserRepository userRepo;

    @Test
    void 대기자_등록_삭제_테스트() {
        // given
        final User user = User.builder().userId("user").build();
        final User savedUser = userRepo.save(user);

        // when
        final PartyWaiting wait = new PartyWaiting(savedUser);
        final PartyWaiting savedWait = waitRepo.save(wait);

        // then
        assertEquals(waitRepo.findById(savedWait.getWaitingId()).get(), savedWait);

        waitRepo.deleteById(savedWait.getWaitingId());
        assertEquals(waitRepo.count(), 0);
    }

    @Test
    void 가장_오래된_대기자_세명_가져오기_테스트() {
        // given
        User[] users = new User[5];
        PartyWaiting[] waits = new PartyWaiting[5];
        for (int i = 0; i < 5; i++) {
            User user = User.builder().userId(String.valueOf(i)).build();
            users[i] = userRepo.save(user);

            PartyWaiting wait = new PartyWaiting(user);
            waits[i] = waitRepo.save(wait);
        }

        waits[0].setCreatedTime(waits[1].getCreatedTime().plusYears(1));
        waitRepo.save(waits[0]);

        // when
        Iterable<PartyWaiting> top3Waits = waitRepo.findTop3ByOrderByCreatedTimeAsc();

        // then
        int userNumber = 1;
        for (PartyWaiting top3Wait : top3Waits) {
            assertEquals(String.valueOf(userNumber), top3Wait.getUser().getUserId());
            userNumber++;
        }
    }
}
