package kr.flab.ottsharing.service;

import javax.transaction.Transactional;

import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.MoneyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MoneyService {
    private final MoneyRepository moneyRepo;

    @Transactional
    public void settle(User user, int amount) {
        user.setMoney(user.getMoney() + amount);
        moneyRepo.save(user);
    }
}