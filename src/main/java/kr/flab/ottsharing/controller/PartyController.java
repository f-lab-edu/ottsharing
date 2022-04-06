package kr.flab.ottsharing.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.PartyCreateResult;
import kr.flab.ottsharing.service.PartyService;
import kr.flab.ottsharing.service.PartyWaitingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyServ;
    private final PartyWaitingService waitingServ;

    @PostMapping("/party/create")
    public PartyCreateResult create(@RequestParam String ottId, @RequestParam String ottPassword) {
        User user = null; // JWT 구현된 후 자동으로 유저 불러오도록 추후 수정
        return partyServ.create(user, ottId, ottPassword);

    }

    @PostMapping("/party/join")
    public String joinParty(@RequestParam String userId) {
        if (partyServ.pickParty().size() != 0) {
            partyServ.getInParty(userId, partyServ.pickParty().get(0));
            return "파티에 참여되었습니다";
        } else {
            waitingServ.putWaitingList(userId);
            return "파티 참여 대기중입니다";
        }
    }

    @DeleteMapping("/party/deleteParty")
    public void deleteParty(@RequestParam String userId, @RequestParam Integer partyId) {
        partyServ.deleteParty(userId, partyId);
    }
}
