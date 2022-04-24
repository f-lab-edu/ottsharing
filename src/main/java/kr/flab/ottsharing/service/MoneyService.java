package kr.flab.ottsharing.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.exception.MoneyException;
import kr.flab.ottsharing.exception.WrongInfoException;
import kr.flab.ottsharing.protocol.PayResult;
import kr.flab.ottsharing.protocol.RefundResult;
import kr.flab.ottsharing.repository.MoneyRepository;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MoneyService {
    
    private final MoneyRepository moneyRepo;
    private final UserRepository userRepository;
    private final PartyMemberRepository memberRepository;

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
        int payDate = filterRefundDate(user.getCreatedTime().getDayOfMonth());

        if(!memberRepository.findOneByUser(user).isPresent() || partyMember == null) {
            throw new WrongInfoException("환불 대상자가 아닙니다.");
        }
         
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int currentDate = now.getDayOfMonth();
        LocalDate thisMonthPay = LocalDate.of(currentYear, currentMonth, payDate);
        LocalDate lastMonthPay = thisMonthPay.minusMonths(1);
        LocalDate nextMonthPay = thisMonthPay.plusMonths(1);
        Long payMoney = checkDisCount(serviceFee,partyMember);
        Long usingPeriod = ChronoUnit.DAYS.between(now, lastMonthPay);
        Long month = ChronoUnit.DAYS.between(thisMonthPay, lastMonthPay);

        if (now.isAfter(thisMonthPay)) {
            month = ChronoUnit.DAYS.between(nextMonthPay, thisMonthPay);
            usingPeriod = ChronoUnit.DAYS.between(now, thisMonthPay);
        }

        Long refundMoney = payMoney - (payMoney / month) * usingPeriod;

        if (payDate == currentDate) {
            refundMoney = payMoney;
        }

        user.setMoney(user.getMoney() + refundMoney);
        moneyRepo.save(user);
        
        return new RefundResult(refundMoney);
    }

    public Long checkDisCount(Long fee, PartyMember partyMember) {
        if(partyMember.isLeader()) {
            return fee - 500L;
        }
        return fee;
    }

    public int filterRefundDate(int date) {
        if(date >= 29) {
            return 28;
        }
        return date;
    }

}