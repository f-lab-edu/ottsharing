package kr.flab.ottsharing.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.service.PartyWaitingService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PartyWaitingController {
    private final PartyWaitingService waitingService;

    @DeleteMapping("/waiting")
    public CommonResponse deleteWaiting(@CookieValue(name = "userId") String userId) {
        return waitingService.deleteWaiting(userId);
    }
}
