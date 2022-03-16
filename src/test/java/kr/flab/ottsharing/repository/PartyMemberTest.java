package kr.flab.ottsharing.repository;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@Transactional
public class PartyMemberTest {

    @Autowired
    private PartyMemberRepository memberRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PartyRepository partyRepo;

    @Test
    void 나의파티찾기_테스트(){

        final User user1 = User.builder().userId("유저1").build();
        final User savedUser = userRepo.save(user1);
        final Party party = Party.builder().leader(savedUser).ottId("id").ottPassword("pass").build();
        final Party myparty = partyRepo.save(party);
        final PartyMember partyMember = PartyMember.builder().user(savedUser).party(myparty).build();

        memberRepo.save(partyMember);

        Party result = memberRepo.findByUser(savedUser).get(0).getParty();
        assertEquals("1",result.getPartyId());

    }


}
