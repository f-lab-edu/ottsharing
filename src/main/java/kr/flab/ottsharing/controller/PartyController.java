package kr.flab.ottsharing.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.protocol.MyParty;
import kr.flab.ottsharing.protocol.PartyCreateResult;
import kr.flab.ottsharing.protocol.PartyDeleteResult;
import kr.flab.ottsharing.protocol.PartyJoinResult;
import kr.flab.ottsharing.protocol.UpdatePartyInfo;
import kr.flab.ottsharing.service.PartyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyServ;

    @PostMapping("/party/create")
    public PartyCreateResult create(@CookieValue(name = "userId") String userId, @RequestParam String ottId, @RequestParam String ottPassword) {
        return partyServ.create(userId, ottId, ottPassword);
    }

    @GetMapping("/myParty")
    public MyParty getMyParty(@CookieValue(name = "userId") String userId) {
        return partyServ.fetchMyParty(userId);
    }

    @PostMapping("/party/join")
    public PartyJoinResult joinParty(@CookieValue(name = "userId") String userId) {
        return partyServ.join(userId);
    }

    @DeleteMapping("/party/deleteParty")
    public PartyDeleteResult deleteParty(@CookieValue(name = "userId") String userId, @RequestParam Integer partyId) {
        return partyServ.deleteParty(userId, partyId);
    }

    @DeleteMapping("/party/getOutParty")
    public String getOutParty(@CookieValue(name = "userId") String userId, @RequestParam Integer partyId) {
        return partyServ.getOutParty(userId, partyId);
    }

    @PostMapping("/party/updatePartyInfo")
    public String updatePartyInfo(@CookieValue(name = "userId") String userId, @Valid @RequestBody UpdatePartyInfo info) {
        return partyServ.updatePartyInfo(userId, info);
    }
}
