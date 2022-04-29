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
        int payDate = filterRefundDate(user.getCreatedTime().getDayOfMonth());

        if(memberRepository.findOneByUser(user).isEmpty()) {
            return new RefundResult(ResultCode.HAS_NO_PARTY);
        }
         
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int currentDate = now.getDayOfMonth();
        LocalDate thisMonthPay = LocalDate.of(currentYear, currentMonth, payDate);
        LocalDate lastMonthPay = thisMonthPay.minusMonths(1);
        LocalDate nextMonthPay = thisMonthPay.plusMonths(1);
        Long payMoney = applyLeaderDiscount(serviceFee,partyMember);
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

    public Long applyLeaderDiscount(Long fee, PartyMember partyMember) {
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