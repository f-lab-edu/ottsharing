package kr.flab.ottsharing.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.DeleteWaitingResult;
import kr.flab.ottsharing.service.PartyWaitingService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PartyWaitingController {
    private final PartyWaitingService waitingService;

    // JWT 구현된 뒤 user 자동으로 들어가도록 해야함
    @DeleteMapping("/waiting")
    public DeleteWaitingResult deleteWaiting(User user) {
        return waitingService.deleteWaiting(user);
    }
}
