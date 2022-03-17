package kr.flab.ottsharing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.protocol.MyParty;
import kr.flab.ottsharing.service.MyPartyService;

@RestController
public class MyPartyController {
    @Autowired
    private MyPartyService myPartyService;

    @GetMapping("/myParty")
    public MyParty viewMyParty(@CookieValue(name = "memberId", required = false) String userId) {
        return myPartyService.getMyParty("유저2");
    }
}
