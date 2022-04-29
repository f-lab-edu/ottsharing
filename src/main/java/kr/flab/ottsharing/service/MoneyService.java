package kr.flab.ottsharing.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.dto.response.RefundResult;
import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.MoneyRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MoneyService {
    
    private final MoneyRepository moneyRepo;
    private final UserRepository userRepository;

    @Value("${ottsharing.serviceFee}")
    private Long serviceFee;

    public ResultCode settle(User user, int amount) {
        user.setMoney(user.getMoney() + amount);
        moneyRepo.save(user);
        return ResultCode.SUCCESS;
    }

    @Transactional
    public ResultCode pay(User user, int amount) {
        if (user.getMoney() < amount) {
            return ResultCode.NOT_ENOUGH_MONEY;
        }

        user.setMoney(user.getMoney() - amount);
        moneyRepo.save(user);

        return ResultCode.SUCCESS;
    }

    public CommonResponse charge(String userId, int moneyToCharge) {
        User user = userRepository.findByUserId(userId).get();
        user.setMoney(user.getMoney()+ moneyToCharge);
        moneyRepo.save(user);

        return new CommonResponse();
    }

    @Transactional
    public CommonResponse withdraw(String userId, int moneyToWithdraw) {
        User user = userRepository.findByUserId(userId).get();
        if (user.getMoney() < moneyToWithdraw) {
            return new CommonResponse(ResultCode.NOT_ENOUGH_MONEY);
        }

        user.setMoney(user.getMoney() - moneyToWithdraw);
        moneyRepo.save(user);

        return new CommonResponse();
    }

    @Transactional
    public RefundResult refund(String userId, PartyMember partyMember) {
        User user = userRepository.findByUserId(userId).get();
        int payDate = user.getCreatedTime().getDayOfMonth();
     
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int currentDate = now.getDayOfMonth();

        Long payMoney = serviceFee;
        Long usingPeriod = 0L;
        Long month = 0L;
        Long usingMoney = 0L;
        Long refundMoney = 0L;

        if (payDate == 29 || payDate == 30 || payDate == 31) {
            payDate = 28;
        }

        LocalDate thisMonthPay = LocalDate.of(currentYear, currentMonth, payDate);
        LocalDate lastMonthPay = thisMonthPay.minusMonths(1);
        LocalDate nextMonthPay = thisMonthPay.plusMonths(1);

        boolean isNowAfterPayDay = now.isAfter(thisMonthPay);
        boolean isLeader = partyMember.isLeader();
        boolean saveMoney = false;

        if (isLeader) {
            payMoney -= 500L;
        }

        if (isNowAfterPayDay) {
            month = ChronoUnit.DAYS.between(nextMonthPay, thisMonthPay);
            usingPeriod = ChronoUnit.DAYS.between(now, thisMonthPay);
            
        }

        if (!isNowAfterPayDay) {
            month = ChronoUnit.DAYS.between(thisMonthPay, lastMonthPay);
            usingPeriod = ChronoUnit.DAYS.between(now, lastMonthPay);
        }

        usingMoney = (payMoney / month) * usingPeriod;
        refundMoney = payMoney - usingMoney;

        if (payDate == currentDate) {
            refundMoney = payMoney;
        }

        if (!saveMoney) {
            user.setMoney(user.getMoney() + refundMoney);
            moneyRepo.save(user);
            saveMoney = true;
        }
        
        return new RefundResult(refundMoney);
    }

}