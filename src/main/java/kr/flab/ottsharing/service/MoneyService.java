package kr.flab.ottsharing.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.exception.MoneyException;
import kr.flab.ottsharing.protocol.PayResult;
import kr.flab.ottsharing.protocol.RefundResult;
import kr.flab.ottsharing.repository.MoneyRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MoneyService {
    
    private final MoneyRepository moneyRepo;
    private final UserRepository userRepository;
    private final PartyMemberService memberService;

    @Value("${ottsharing.serviceFee}")
    private Long serviceFee;

    public void settle(User user, int amount) {
        user.setMoney(user.getMoney() + amount);
        moneyRepo.save(user);
    }

    @Transactional
    public PayResult pay(User user, int amount) {
        if (user.getMoney() < amount) {
            return PayResult.NOT_ENOUGH_MONEY;
        }

        user.setMoney(user.getMoney() - amount);
        moneyRepo.save(user);

        return PayResult.SUCCESS;
    }

    public String charge(String userId, int moneyToCharge) {

        User user = userRepository.findByUserId(userId).get();
        user.setMoney(user.getMoney()+ moneyToCharge);
        moneyRepo.save(user);

        return "충전 완료되었습니다";
    }

    @Transactional
    public String withdraw(String userId, int moneyToWithdraw) {
        User user = userRepository.findByUserId(userId).get();
        if (user.getMoney() < moneyToWithdraw) {
            throw new MoneyException("현재 돈이 부족합니다.");
        }

        user.setMoney(user.getMoney() - moneyToWithdraw);
        moneyRepo.save(user);
        return "출금 완료되었습니다";
    }

    @Transactional
    public RefundResult refund(String userId, PartyMember partyMember) {
        User user = userRepository.findByUserId(userId).get();
        int payDate = user.getCreatedTime().getDayOfMonth();
     
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int currentDate = now.getDayOfMonth();

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
        boolean isLeader = memberService.checkLeader(partyMember);

        if (isLeader) {
            serviceFee -= 500L;
        }

        if (payDate == currentDate) {
            return new RefundResult(serviceFee);
        }

        if (isNowAfterPayDay) {
            month = ChronoUnit.DAYS.between(nextMonthPay, thisMonthPay);
            usingPeriod = ChronoUnit.DAYS.between(now, thisMonthPay);
            
        }

        if (!isNowAfterPayDay) {
            month = ChronoUnit.DAYS.between(thisMonthPay, lastMonthPay);
            usingPeriod = ChronoUnit.DAYS.between(now, lastMonthPay);
        }

        usingMoney = (serviceFee / month) * usingPeriod;
        refundMoney = serviceFee - usingMoney;
        user.setMoney(user.getMoney() + refundMoney);
        moneyRepo.save(user);
        
        return new RefundResult(refundMoney);
    }

}