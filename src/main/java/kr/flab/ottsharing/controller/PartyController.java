package kr.flab.ottsharing.controller;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.service.PartyService;
import kr.flab.ottsharing.service.PartyWaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyServ;
    private final PartyWaitingService waitingServ;

    @PostMapping("/party/create")
    public void createParty(@RequestParam String leaderId, @RequestParam String ottId, @RequestParam String ottPassword) {

        Party enrolledParty = partyServ.enrollParty(leaderId, ottId, ottPassword);

        if (waitingServ.cheackWaitingPersonExist()) {
            Integer membernumber = waitingServ.putPartyMember(enrolledParty);

            if (membernumber == 3) {
                partyServ.makeFull(enrolledParty);
            }

        }
    }

    @PostMapping("/party/join")
    public String joinParty(@RequestParam String userId){

        if(partyServ.pickParty().size() != 0) {
            partyServ.getInParty(userId,partyServ.pickParty().get(0));
            return "파티에 참여되었습니다";
        } else{
            waitingServ.putWaitingList(userId);
            return "파티 참여 대기중입니다";
        }

    }
}
