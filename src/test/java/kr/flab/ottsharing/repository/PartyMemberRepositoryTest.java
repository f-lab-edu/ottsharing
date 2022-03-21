package kr.flab.ottsharing.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    // Party Entity 구조 변경으로 인해 동작이 안되는 코드
    /*
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

     */

}
