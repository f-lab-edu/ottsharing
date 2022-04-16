package kr.flab.ottsharing.service;

import javax.transaction.Transactional;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.PayResult;
import kr.flab.ottsharing.repository.MoneyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MoneyService {
    private final MoneyRepository moneyRepo;

    @Transactional
    public PayResult pay(User user, int amount) {
        if (user.getMoney() < amount) {
            return PayResult.NOT_ENOUGH_MONEY;
        }

        user.setMoney(user.getMoney() - amount);
        moneyRepo.save(user);

        return PayResult.SUCCESS;
    }
}
