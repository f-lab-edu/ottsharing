package kr.flab.ottsharing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.flab.ottsharing.service.MoneyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;

    @PostMapping("/charge")
    public String chargeMoney(@RequestParam String userId, @RequestParam int moneyToCharge) {
        return moneyService.charge(userId, moneyToCharge);
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@RequestParam String userId, @RequestParam int moneyToWithdraw) {
        return moneyService.withdraw(userId, moneyToWithdraw);
    }
    
}
