package kr.flab.ottsharing.repository;

import static org.junit.jupiter.api.Assertions.*;

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
    void save_테스트() {
        // given
        final User user = User.builder().userId("user").build();
        final User savedUser = userRepo.save(user);

        // when
        final PartyWaiting wait = PartyWaiting.builder().user(savedUser).build();

        // then
        final PartyWaiting savedWait = waitRepo.save(wait);
        assertEquals(waitRepo.findById(savedWait.getWaitingId()).get(), savedWait);
    }

    @Test
    void deleteByUser_테스트() {
        // given
        final User user = User.builder().userId("user").build();
        final User savedUser = userRepo.save(user);

        // when
        final PartyWaiting wait = PartyWaiting.builder().user(savedUser).build();
        waitRepo.save(wait);

        // then
        PartyWaiting waiting = waitRepo.findByUser(user).get();
        waitRepo.delete(waiting);
        assertEquals(waitRepo.count(), 0);
    }

    @Test
    void findTop3ByOrderByCreatedTimeAsc_테스트() {
        // given
        User[] users = new User[5];
        PartyWaiting[] waits = new PartyWaiting[5];
        for (int i = 0; i < 5; i++) {
            User user = User.builder().userId(String.valueOf(i)).build();
            users[i] = userRepo.save(user);

            PartyWaiting wait = PartyWaiting.builder().user(user).build();
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

    @Test
    void existsByUser_테스트() {
        // given
        final User user1 = User.builder().userId("user1").build();
        final User savedUser1 = userRepo.save(user1);

        final PartyWaiting wait = PartyWaiting.builder().user(savedUser1).build();
        waitRepo.save(wait);

        final User user2 = User.builder().userId("user2").build();
        final User savedUser2 = userRepo.save(user2);


        // then
        assertEquals(true, waitRepo.existsByUser(savedUser1));
        assertEquals(false, waitRepo.existsByUser(savedUser2));
    }

    @Test
    void existsBy_테스트() {
        assertEquals(false, waitRepo.existsBy());

        final User user = User.builder().userId("user").build();
        final User savedUser = userRepo.save(user);

        final PartyWaiting wait = PartyWaiting.builder().user(savedUser).build();
        waitRepo.save(wait);

        assertEquals(true, waitRepo.existsBy());
    }
}
