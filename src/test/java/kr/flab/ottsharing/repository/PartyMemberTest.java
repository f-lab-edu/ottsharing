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

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
public class PartyMemberTest {

    @Autowired
    private PartyMemberRepository memberRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PartyRepository partyRepo;

    @Test
    void 나의파티찾기_파티원목록조회_테스트(){
        //given
        final User user1 = User.builder().userId("유저1").build();
        final User savedUser1 = userRepo.save(user1);
        final User user2 = User.builder().userId("유저2").build();
        final User savedUser2 = userRepo.save(user2);
        final User user3 = User.builder().userId("유저3").build();
        final User savedUser3 = userRepo.save(user3);

        final Party party = Party.builder().leader(savedUser1).ottId("id").ottPassword("pass").build();
        final Party savedParty = partyRepo.save(party);

        //when
        final PartyMember partyMember2 = PartyMember.builder().user(savedUser2).party(savedParty).build();
        memberRepo.save(partyMember2);
        final PartyMember partyMember3 = PartyMember.builder().user(savedUser3).party(savedParty).build();
        memberRepo.save(partyMember3);

        //then
        assertEquals(savedParty, memberRepo.findPartyByUserId(savedUser2).get());

        List<User> members = memberRepo.findUsersByParty(savedParty);
        assertEquals(true, members.contains(savedUser2));
        assertEquals(true, members.contains(savedUser3));
    }

}