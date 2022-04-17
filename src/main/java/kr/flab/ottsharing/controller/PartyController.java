package kr.flab.ottsharing.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.MyParty;
import kr.flab.ottsharing.protocol.PartyCreateResult;
import kr.flab.ottsharing.protocol.UpdatePartyInfo;
import kr.flab.ottsharing.service.PartyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyServ;

    @PostMapping("/party/create")
    public PartyCreateResult create(@RequestParam String ottId, @RequestParam String ottPassword) {
        User user = null; // JWT 구현된 후 자동으로 유저 불러오도록 추후 수정
        return partyServ.create(user, ottId, ottPassword);
    }

    @GetMapping("/myParty")
    public MyParty getMyParty(User user) { // JWT 구현된 후 자동으로 유저 불러오도록 추후 수정
        return partyServ.fetchMyParty(user);
    }

    // PartyWaiting 구조 변경으로 동작하지 않는 코드
    @PostMapping("/party/join")
    public String joinParty(@RequestParam String userId) {
        /*
        if (partyServ.pickParty().size() != 0) {
            partyServ.getInParty(userId, partyServ.pickParty().get(0));
            return "파티에 참여되었습니다";
        } else {
            waitingServ.putWaitingList(userId);
            return "파티 참여 대기중입니다";
        }
        */
        return null;
    }

    @DeleteMapping("/party/deleteParty")
    public String deleteParty(@RequestParam String userId, @RequestParam Integer partyId) {
        return partyServ.deleteParty(userId, partyId);
    }

    @DeleteMapping("/party/getOutParty")
    public String getOutParty(@RequestParam String userId, @RequestParam Integer partyId) {
        return partyServ.getOutParty(userId, partyId);
    }

    @PostMapping("/party/updatePartyInfo")
    public String updatePartyInfo(@Valid @RequestBody UpdatePartyInfo info) {
        return partyServ.updatePartyInfo(info);
    }
}
