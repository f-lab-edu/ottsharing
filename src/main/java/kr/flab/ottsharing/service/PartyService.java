package kr.flab.ottsharing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.exception.WrongInfoException;
import kr.flab.ottsharing.protocol.MyParty;
import kr.flab.ottsharing.protocol.PartyCreateResult;
import kr.flab.ottsharing.protocol.PartyMemberInfo;
import kr.flab.ottsharing.protocol.PartyJoinResult;
import kr.flab.ottsharing.protocol.PayResult;
import kr.flab.ottsharing.protocol.UpdatePartyInfo;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.PartyRepository;
import kr.flab.ottsharing.repository.PartyWaitingRepository;
import kr.flab.ottsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepo;
    private final PartyMemberRepository memberRepo;
    private final UserRepository userRepo;
    private final PartyWaitingRepository waitingRepo;
    private final PartyMemberService memberService;
    private final MoneyService moneyService;
    private final PartyWaitingService waitingService;

    @Value("${ottsharing.serviceFee}")
    private int serviceFee;

    public PartyCreateResult create(String leaderId, String ottId, String ottPassword) {
        User leader = userRepo.findByUserId(leaderId).get();

        Party party = Party.builder()
            .ottId(ottId)
            .ottPassword(ottPassword)
            .build();
        partyRepo.save(party);

        PartyMember member = PartyMember.builder()
            .user(leader)
            .isLeader(true)
            .party(party)
            .nickname(leader.getUserId())
            .build();
        memberRepo.save(member);

        return PartyCreateResult.SUCCESS;
    }

    @Transactional
    public String deleteParty(String userId, Integer partyId) {

        Optional<User> user = userRepo.findByUserId(userId);
        if (!user.isPresent()) {
            throw new WrongInfoException("존재하지 않는 회원id를 입력했습니다" + userId );
        }
        User presentUser = user.get();
        PartyMember partymember = memberRepo.findOneByUser(presentUser).get();

        if (!memberService.checkLeader(partymember)) {
            throw new WrongInfoException("삭제 권한이 없습니다" + userId );
        }

        Party party = memberService.getParty(partymember);

        if (!party.getPartyId().equals(partyId)) {
            throw new WrongInfoException("삭제 권한의 그룹이 아닙니다" + partyId );
        }

        memberRepo.deleteAllByParty(party);
        partyRepo.deleteById(partyId);

        return "삭제 완료되었습니다";
    }
    
    @Transactional
    public String getOutParty(String userId, Integer partyId) {
        Optional<User> user = userRepo.findByUserId(userId);

        if (!user.isPresent()) {
            throw new WrongInfoException("존재하지 않는 회원id를 입력했습니다" + userId );
        }
        
        User currentUser = user.get();
        PartyMember partyMember = memberRepo.findOneByUser(currentUser).get();

        if (memberService.checkLeader(partyMember)) {
            throw new WrongInfoException("리더인 경우, 탈퇴가 아닌 파티해체 절차로 가주세요" + userId );
        }

        Party party = memberService.getParty(partyMember);

        if (!party.getPartyId().equals(partyId)) {
            throw new WrongInfoException("탈퇴 권한의 그룹이 아닙니다" + partyId );
        }

        memberRepo.deleteById(partyMember.getPartyMemberId());
        moneyService.refund(currentUser.getUserId(),partyMember);
        party.setFull(false);
        partyRepo.save(party);

        return "파티 탈퇴 완료되었습니다";
    }

    public MyParty fetchMyParty(String userId) {
        User user = userRepo.findByUserId(userId).get();

        Optional<PartyMember> joined = memberRepo.findOneByUser(user);
        if (joined.isPresent()) {
            Party party = joined.get().getParty();

            List<PartyMember> members = memberRepo.findByParty(party);
            List<PartyMemberInfo> memberInfos = new ArrayList<>();
            for (PartyMember member : members) {
                PartyMemberInfo memberInfo = PartyMemberInfo.builder()
                    .memberId(member.getUser().getUserId())
                    .nickname(member.getNickname())
                    .isLeader(member.isLeader())
                    .build();
                memberInfos.add(memberInfo);
            }
            
            return new MyParty(memberInfos, party.getOttId(), party.getOttPassword());
        }

        if (waitingRepo.existsByUser(user)) {
            return new MyParty(MyParty.Status.WAITING_FOR_PARTY);
        }

        return new MyParty(MyParty.Status.HAS_NO_PARTY);
    }

    public String updatePartyInfo(String userId, UpdatePartyInfo info) {
        User user = userRepo.findByUserId(userId).get();
        PartyMember partyMember = memberRepo.findOneByUser(user).get();
        Integer partyId = partyMember.getParty().getPartyId();
        Integer writtenPartyId = info.getPartyId();

        if(!partyId.equals(writtenPartyId)) {
            throw new WrongInfoException("해당 파티에 속하지 않았습니다. 파티id를 다시 바르게 입력해주세요"  );
        }
        
        if (memberService.checkLeader(partyMember)) {
            return memberService.changeInfoOfLeader(partyMember, partyMember.getParty(), info);
        }
        
        return memberService.changeInfoOfMember(partyMember, partyMember.getParty(), info);
    }


    @Transactional
    public PartyJoinResult join(String userId) {
        User user = userRepo.findByUserId(userId).get();

        PayResult payResult = moneyService.pay(user, serviceFee);
        if (payResult == PayResult.NOT_ENOUGH_MONEY) {
            return PartyJoinResult.NOT_ENOUGH_MONEY; 
        } 
        
        Optional<Party> anyNotFullParty = getAnyNotFullParty();
        if (anyNotFullParty.isEmpty()) {
            waitingService.addWaiting(user);
            return PartyJoinResult.ON_WAITING;
        }

        memberService.join(anyNotFullParty.get(), user);
        return PartyJoinResult.SUCCESS;
    }

    private Optional<Party> getAnyNotFullParty() {
        List<Party> parties = partyRepo.findNotFullOldestParties();
        if (parties.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(parties.get(0));
    }

    // 추후 변경해야 할 코드
    public boolean makeFull(Party party) {
/*
        party.setFull(true);
        partyRepo.save(party);
*/
        return true;
    }

    // Party Repository 구조 변경으로 인해 동작하지 않는 코드
    public List<Party> pickParty() {
        /*
        List<Party> notFullParties = (List<Party>) partyRepo.findByIsFullFalse();
        return notFullParties;
         */
        return null;
    }

    // Party Repository 구조 변경으로 인해 동작하지 않는 코드
    public void getInParty(String userId, Party pickParty){
        /*User userToJoin = userRepo.getById(userId);
        PartyMember member = PartyMember.builder().user(userToJoin).party(pickParty).build();
        memberRepo.save(member);*/
    }
}
