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
import kr.flab.ottsharing.repository.MoneyRepository;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MoneyService {
    
    private final MoneyRepository moneyRepo;
    private final UserRepository userRepository;
    private final PartyMemberService memberService;
    private final PartyMemberRepository memberRepo;

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
    public String refund(String userId) {
        User user = userRepository.findByUserId(userId).get();
        int payDate = user.getCreatedTime().getDayOfMonth();
        PartyMember partymember = memberRepo.findOneByUser(user).get();
     
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        LocalDate thisMonthPay = LocalDate.of(currentYear,currentMonth,payDate);
        LocalDate lastMonthPay = thisMonthPay.minusMonths(1);
        LocalDate nextMonthPay = thisMonthPay.plusMonths(1);

        boolean isNowAfterPayDay = now.isAfter(thisMonthPay);
        boolean isLeader = memberService.checkLeader(partymember);

        Long usingPeriod = 0L;
        Long month = 0L;
        Long usingMoney = 0L;
        Long refundMoney = 0L;
  
        if (isLeader) {
            serviceFee -= 500L;
        }

        if(isNowAfterPayDay) {
            month = ChronoUnit.DAYS.between(nextMonthPay,thisMonthPay);
            usingPeriod = ChronoUnit.DAYS.between(now,thisMonthPay);
            
        }

        if(!isNowAfterPayDay) {
            month = ChronoUnit.DAYS.between(thisMonthPay,lastMonthPay);
            usingPeriod = ChronoUnit.DAYS.between(now,lastMonthPay);
        }

        usingMoney = (serviceFee / month) * usingPeriod;
        refundMoney = serviceFee - usingMoney;
        user.setMoney(user.getMoney() + refundMoney);
        moneyRepo.save(user);
        
        return "가상머니로 " + refundMoney +"원 환불 완료되었습니다.";
    }

}