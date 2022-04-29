package kr.flab.ottsharing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.entity.PartyWaiting;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.PartyWaitingRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyWaitingService {
    private final PartyWaitingRepository waitRepo;
    private final UserRepository userRepo;

    public boolean checkWaitingPersonExist() {
        return waitRepo.existsBy();
    }
  
    public void addWaiting(User user) {
        PartyWaiting waiting = PartyWaiting.builder().user(user).build();
        waitRepo.save(waiting);
    }
  
    public CommonResponse deleteWaiting(String userId) {
      User user = userRepo.findByUserId(userId).get();
      PartyWaiting waiting = waitRepo.findByUser(user).get();
      waitRepo.delete(waiting);
      return new CommonResponse();
    }
    
    public List<User> getTop3Waitings() {
        List<PartyWaiting> top3Waits = waitRepo.findTop3ByOrderByCreatedTimeAsc();
        List<User> top3Users = new ArrayList<>();

        for (PartyWaiting top3Wait : top3Waits) {
            top3Users.add(top3Wait.getUser());
        }
        
        return top3Users;
    }
}
