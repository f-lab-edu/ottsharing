package kr.flab.ottsharing.service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.PartyWaitingRepository;
import kr.flab.ottsharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.protocol.MyParty;

import java.util.Optional;

@Service
public class MyPartyService {
    @Autowired
    private PartyMemberRepository memberRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PartyWaitingRepository waitRepo;


    public MyParty getMyParty(String userId) {
        // User Repository 구조 변경으로 인해 코드 동작하지 않음
        /*
        if (userId == null || userId.isEmpty()) {
            return new MyParty(MyParty.Status.NOT_LOGIN);
        }

        User user = userRepo.getById(userId);
        Optional<Party> optionalParty = memberRepo.findPartyByUser(user);
        if (optionalParty.isPresent()) {
            Party party = optionalParty.get();
            return new MyParty(party.getLeader(), memberRepo.findUsersByParty(party), party.getOttId(), party.getOttPassword());
        }

        if (waitRepo.existsByUser(user)) {
            return new MyParty(MyParty.Status.WAITING_FOR_PARTY);
        }

        return new MyParty(MyParty.Status.HAS_NO_PARTY);
        */
        return null;
    }
}
