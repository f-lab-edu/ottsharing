package kr.flab.ottsharing.service;

import java.util.List;
import org.springframework.stereotype.Service;
import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.PartyRepository;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.PartyCreateResult;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepo;
    private final PartyMemberRepository memberRepo;

    public PartyCreateResult create(User leader, String ottId, String ottPassword) {
        Party party = Party.builder()
            .ottId(ottId)
            .ottPassword(ottPassword)
            .build();
        partyRepo.save(party);
        
        PartyMember member = PartyMember.builder()
            .user(leader)
            .isLeader(true)
            .party(party)
            .nickname(leader.getUserId())
            .build();
        memberRepo.save(member);

        return PartyCreateResult.SUCCESS;
    }


    // Party Entity 구조 변경으로 인해 동작하지 않는 코드
    public Party enrollParty(String leaderId, String getottId, String getottPassword) {
        // User Repository 구조 개편으로 코드 정상적으로 동작하지 않음
        /*

        /*User teamleader = userRepo.getById(leaderId);
        Party party = Party.builder().leader(teamleader).ottId(getottId).ottPassword(getottPassword).build();
        Party enrolledParty = partyRepo.save(party);

        return enrolledParty; */
        return null;
    }

    // 추후 변경해야 할 코드
    public boolean makeFull(Party party) {
/*
        party.setFull(true);
        partyRepo.save(party);
*/
        return true;
    }

    // Party Repository 구조 변경으로 인해 동작하지 않는 코드
    public List<Party> pickParty() {
        /*
        List<Party> notFullParties = (List<Party>) partyRepo.findByIsFullFalse();
        return notFullParties;
         */
        return null;
    }

    // Party Repository 구조 변경으로 인해 동작하지 않는 코드
    public void getInParty(String userId, Party pickParty){
        /*User userToJoin = userRepo.getById(userId);
        PartyMember member = PartyMember.builder().user(userToJoin).party(pickParty).build();
        memberRepo.save(member);*/
    }
}
