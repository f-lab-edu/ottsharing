package kr.flab.ottsharing.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.PayResult;
import kr.flab.ottsharing.repository.MoneyRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MoneyService {
    
    private final MoneyRepository moneyRepo;
    private final UserRepository userRepository;

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
}