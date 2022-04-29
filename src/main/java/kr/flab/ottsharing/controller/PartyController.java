package kr.flab.ottsharing.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.dto.request.PartyCreateDto;
import kr.flab.ottsharing.dto.request.PartyIdDto;
import kr.flab.ottsharing.dto.request.PartyUpdateDto;
import kr.flab.ottsharing.dto.response.MyParty;
import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.service.PartyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyServ;

    @PostMapping("/party/create")
    public CommonResponse create(@CookieValue(name = "userId") String userId, @RequestBody PartyCreateDto createDto) {
        return partyServ.create(userId, createDto.getOttId(), createDto.getOttPassword());
    }

    @GetMapping("/myParty")
    public MyParty getMyParty(@CookieValue(name = "userId") String userId) {
        return partyServ.fetchMyParty(userId);
    }

    @PostMapping("/party/join")
    public CommonResponse joinParty(@CookieValue(name = "userId") String userId) {
        return partyServ.join(userId);
    }

    @DeleteMapping("/party/deleteParty")
    public CommonResponse deleteParty(@CookieValue(name = "userId") String userId, @RequestBody PartyIdDto partyIdDto) {
        return partyServ.deleteParty(userId, partyIdDto.getPartyId());
    }

    @DeleteMapping("/party/getOutParty")
    public CommonResponse getOutParty(@CookieValue(name = "userId") String userId, @RequestBody PartyIdDto partyIdDto) {
        return partyServ.getOutParty(userId, partyIdDto.getPartyId());
    }

    @PostMapping("/party/updatePartyInfo")
    public CommonResponse updatePartyInfo(@CookieValue(name = "userId") String userId, @Valid @RequestBody PartyUpdateDto info) {
        return partyServ.updatePartyInfo(userId, info);
    }
}
