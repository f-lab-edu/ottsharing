package kr.flab.ottsharing.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
public class PartyMemberRepositoryTest {
    @Autowired
    private PartyMemberRepository memberRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PartyRepository partyRepo;

    @Test
    void User로_PartyMember_행_찾기_테스트() {
        //given
        final User user1 = User.builder().userId("유저1").build();
        final User savedUser1 = userRepo.save(user1);
        final User user2 = User.builder().userId("유저2").build();
        final User savedUser2 = userRepo.save(user2);
        final User user3 = User.builder().userId("유저3").build();
        final User savedUser3 = userRepo.save(user3);

        final Party party = Party.builder().ottId("id").ottPassword("pass").build();
        final Party savedParty = partyRepo.save(party);

        //when
        final PartyMember partyMember1 = PartyMember.builder().user(savedUser1)
            .isLeader(true).party(savedParty).build();
        memberRepo.save(partyMember1);
        final PartyMember partyMember2 = PartyMember.builder().user(savedUser2).party(savedParty).build();
        memberRepo.save(partyMember2);
        final PartyMember partyMember3 = PartyMember.builder().user(savedUser3).party(savedParty).build();
        memberRepo.save(partyMember3);

        //then
        assertEquals(partyMember2, memberRepo.findOneByUser(savedUser2).get());
    }

    @Test
    void Party로_PartyMember들_찾기_테스트() {
        //given
        final User user1 = User.builder().userId("유저1").build();
        final User savedUser1 = userRepo.save(user1);
        final User user2 = User.builder().userId("유저2").build();
        final User savedUser2 = userRepo.save(user2);
        final User user3 = User.builder().userId("유저3").build();
        final User savedUser3 = userRepo.save(user3);

        final Party party = Party.builder().ottId("id").ottPassword("pass").build();
        final Party savedParty = partyRepo.save(party);

        //when
        final PartyMember partyMember1 = PartyMember.builder().user(savedUser1)
            .isLeader(true).party(savedParty).build();
        memberRepo.save(partyMember1);
        final PartyMember partyMember2 = PartyMember.builder().user(savedUser2).party(savedParty).build();
        memberRepo.save(partyMember2);
        final PartyMember partyMember3 = PartyMember.builder().user(savedUser3).party(savedParty).build();
        memberRepo.save(partyMember3);

        //then
        List<PartyMember> members = memberRepo.findByParty(savedParty);
        assertEquals(true, members.contains(partyMember1));
        assertEquals(true, members.contains(partyMember2));
        assertEquals(true, members.contains(partyMember3));
    }
}
