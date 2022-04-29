package kr.flab.ottsharing.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import kr.flab.ottsharing.dto.request.MoneyDto;
import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.service.MoneyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;

    @PostMapping("/charge")
    public CommonResponse chargeMoney(@CookieValue(name = "userId") String userId, @RequestBody MoneyDto chargeDto) {
        return moneyService.charge(userId, chargeDto.getAmount());
        
    }

    @PostMapping("/withdraw")
    public CommonResponse withdrawMoney(@CookieValue(name = "userId") String userId, @RequestBody MoneyDto withdrawDto) {
        return moneyService.withdraw(userId, withdrawDto.getAmount());
    }
    
}
