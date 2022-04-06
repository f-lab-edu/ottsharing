package kr.flab.ottsharing.service;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyMemberService {

    private final PartyMemberRepository memberRepo;
    private PartyMember member;

    public Boolean checkLeader(User user){
        member = memberRepo.findOneByUser(user).get();
        return member.isLeader();
    }

    public Party PartyOfLeader(){
        return member.getParty();
    }
    
}
