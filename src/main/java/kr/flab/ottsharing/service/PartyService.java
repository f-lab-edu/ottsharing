package kr.flab.ottsharing.service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.PartyWaiting;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.PartyRepository;
import kr.flab.ottsharing.repository.PartyWaitingRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final UserRepository userRepo;
    private final PartyRepository partyRepo;
    private final PartyMemberRepository memberRepo;

    public Party enrollParty(String leaderId,String getottId, String getottPassword){

        User teamleader = userRepo.getById(leaderId);
        Party party = Party.builder().leader(teamleader).ottId(getottId).ottPassword(getottPassword).build();
        Party enrolledParty = partyRepo.save(party);

        return enrolledParty;
    }

    public boolean makeFull(Party party){

        party.setFull(true);
        partyRepo.save(party);

        return true;
    }

    public List<Party> pickParty(){
        List<Party> notFullParties = (List<Party>) partyRepo.findByIsFullFalse();
        return notFullParties;
    }

    public void getInParty(String userId, Party pickParty){
        User userToJoin = userRepo.getById(userId);
        PartyMember member = PartyMember.builder().user(userToJoin).party(pickParty).build();
        memberRepo.save(member);
    }

}
