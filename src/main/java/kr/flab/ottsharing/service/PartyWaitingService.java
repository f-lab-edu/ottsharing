package kr.flab.ottsharing.service;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyWaiting;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.DeleteWaitingResult;
import kr.flab.ottsharing.repository.PartyWaitingRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyWaitingService {
    private final PartyWaitingRepository waitRepo;
    private final UserRepository userRepo;

    public boolean checkWaitingPersonExist() {
        return waitRepo.existsBy();
    }
  
    public void addWaiting(User user) {
        PartyWaiting waiting = PartyWaiting.builder().user(user).build();
        waitRepo.save(waiting);
    }
  
    public DeleteWaitingResult deleteWaiting(String userId) {
      User user = userRepo.findByUserId(userId).get();
      waitRepo.deleteByUser(user);
      return DeleteWaitingResult.SUCCESS;
    }

    //추후 변경해야 할 코드
    public Integer putPartyMember(Party enrolledparty) {
        /*
        Iterable<PartyWaiting> top3Waits = waitRepo.findTop3ByOrderByCreatedTimeAsc();
        int memberNumber = 0;
        for (PartyWaiting top3Wait : top3Waits) {
            PartyMember member = PartyMember.builder().user(top3Wait.getUser()).party(enrolledparty).build();
            memberRepo.save(member);
            waitRepo.deleteById(top3Wait.getWaitingId());
            memberNumber++;
        }

        return memberNumber;
        */
        return 0;
    }
}
