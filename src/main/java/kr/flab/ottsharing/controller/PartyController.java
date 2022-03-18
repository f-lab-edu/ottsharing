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
}
