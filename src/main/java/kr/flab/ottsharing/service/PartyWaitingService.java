package kr.flab.ottsharing.service;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.DeleteWaitingResult;
import kr.flab.ottsharing.repository.PartyWaitingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyWaitingService {

    private  final PartyWaitingRepository waitRepo;

    public boolean cheackWaitingPersonExist() {
        return waitRepo.existsBy();
    }

    public DeleteWaitingResult deleteWaiting(User user) {
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

    // User Repository 구조 변경으로 인해 동작하지 않는 코드
    public void putWaitingList(String waitmemberId) {
        /*User waitmember = userRepo.getById(waitmemberId);
        PartyWaiting member = PartyWaiting.builder().user(waitmember).build();
        waitRepo.save(member);*/
    }
}
