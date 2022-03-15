package kr.flab.ottsharing.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
public class PartyRepositoryTest {
    @Autowired
    private PartyRepository partyRepo;
    @Autowired
    private UserRepository userRepo;

    @Test
    void 파티_생성_및_조회_테스트() {
        // given
        final User user = User.builder().userId("user").build();
        final User savedUser = userRepo.save(user);

        final Party party = Party.builder().leader(savedUser).ottId("id").ottPassword("pass").build();

        // when
        partyRepo.save(party);

        // then
        Optional<Party> optionalParty = partyRepo.findById(party.getPartyId());
        assertEquals(true, optionalParty.isPresent());

        Party savedParty = optionalParty.get();
        assertEquals("user", savedParty.getLeader().getUserId());
        assertEquals("id", savedParty.getOttId());
        assertEquals("pass", savedParty.getOttPassword());
        assertEquals(false, savedParty.isFull());
    }

    @Test
    void 빈_파티_목록_찾기_테스트() {
        // given
        final User user1 = User.builder().userId("user1").build();
        final User savedUser1 = userRepo.save(user1);
        final User user2 = User.builder().userId("user2").build();
        final User savedUser2 = userRepo.save(user2);
        final User user3 = User.builder().userId("user3").build();
        final User savedUser3 = userRepo.save(user3);

        final Party party1 = Party.builder().leader(savedUser1).ottId("id1").ottPassword("pass1").build();
        final Party party2 = Party.builder().leader(savedUser2).ottId("id2").ottPassword("pass2").isFull(true).build();
        final Party party3 = Party.builder().leader(savedUser3).ottId("id3").ottPassword("pass3").build();
        partyRepo.save(party1);
        partyRepo.save(party2);
        partyRepo.save(party3);

        // when
        List<Party> notFullParties = (List<Party>) partyRepo.findByIsFullFalse();

        // then
        assertEquals(2, notFullParties.size());
    }
}
