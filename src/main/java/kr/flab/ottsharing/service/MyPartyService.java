package kr.flab.ottsharing.service;
import org.springframework.stereotype.Service;
import kr.flab.ottsharing.protocol.MyParty;


@Service
public class MyPartyService {
  

    // Party Entity 구조 변경으로 인해 동작하지 않는 코드
    public MyParty getMyParty(String userId) {
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
