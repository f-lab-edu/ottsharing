package kr.flab.ottsharing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.protocol.PartyInfo;
import kr.flab.ottsharing.protocol.PartyMemberInfo;
import kr.flab.ottsharing.repository.PartyRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepo;
    private final PartyMemberService memberService;

    public PartyInfo fetchParty(Party party) {
        List<PartyMemberInfo> memberInfos = memberService.getPartyMemberInfos(party);

        return PartyInfo.builder()
            .ottId(party.getOttId())
            .ottPassword(party.getOttPassword())
            .members(memberInfos)
            .created(party.getCreatedTime())
            .build();
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

    public boolean makeFull(Party party){

        party.setFull(true);
        partyRepo.save(party);

        return true;
    }

    // Party Repository 구조 변경으로 인해 동작하지 않는 코드
    public List<Party> pickParty(){
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