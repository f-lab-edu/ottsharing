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

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
public class PartyRepositoryTest {
    @Autowired
    private PartyRepository partyRepo;

    @Test
    void 파티_생성_및_조회_테스트() {
        // given
        final Party party = Party.builder().ottId("id").ottPassword("pass").build();

        // when
        partyRepo.save(party);

        // then
        Optional<Party> optionalParty = partyRepo.findById(party.getPartyId());
        assertEquals(true, optionalParty.isPresent());

        Party savedParty = optionalParty.get();
        assertEquals("id", savedParty.getOttId());
        assertEquals("pass", savedParty.getOttPassword());
        assertEquals(false, savedParty.isFull());
    }

    @Test
    void 자리가_있고_가장_오래된_파티_찾기_테스트() {
        // given
        final Party party1 = Party.builder().ottId("id1").ottPassword("pass1").build();
        final Party party2 = Party.builder().ottId("id2").ottPassword("pass2").isFull(true).build();
        final Party party3 = Party.builder().ottId("id3").ottPassword("pass3").build();

        partyRepo.save(party1);
        partyRepo.save(party2);
        partyRepo.save(party3);

        party3.setCreatedTime(party3.getCreatedTime().minusYears(1));
        partyRepo.save(party3);

        // when
        List<Party> optionalEarliestParties = partyRepo.findNotFullOldestParties();

        // then
        assertEquals(2, optionalEarliestParties.size());
        assertEquals(party3.getPartyId(), optionalEarliestParties.get(0).getPartyId());
    }
}
