package kr.flab.ottsharing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyWaiting;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.DeleteWaitingResult;
import kr.flab.ottsharing.repository.PartyWaitingRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyWaitingService {
    private final MoneyService moneyService;
    private final PartyWaitingRepository waitRepo;
    private final UserRepository userRepo;

    public boolean checkWaitingPersonExist() {
        return waitRepo.existsBy();
    }
  
    public void addWaiting(User user) {
        PartyWaiting waiting = PartyWaiting.builder().user(user).build();
        waitRepo.save(waiting);
    }
  
    public DeleteWaitingResult deleteWaiting(String userId) {
      User user = userRepo.findByUserId(userId).get();
      waitRepo.deleteByUser(user);
      return DeleteWaitingResult.SUCCESS;
    }

    public List<User> getTop3Waitings() {
        List<PartyWaiting> waits = waitRepo.findByOrderByCreatedTimeAsc();
        List<User> top3Users = new ArrayList<>();

        for (PartyWaiting wait : waits) {
            if (top3Users.size() >= 3) break;
            User user = wait.getUser();

            if (moneyService.hasEnoughMoney(user)) {
                top3Users.add(user);
            }
        }
        
        return top3Users;
    }
}
