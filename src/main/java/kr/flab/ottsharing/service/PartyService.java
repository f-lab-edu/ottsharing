package kr.flab.ottsharing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.flab.ottsharing.dto.request.PartyUpdateDto;
import kr.flab.ottsharing.dto.response.MyParty;
import kr.flab.ottsharing.dto.response.PartyMemberInfo;
import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
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

    public CommonResponse create(String leaderId, String ottId, String ottPassword) {
        User leader = userRepo.findByUserId(leaderId).get();

        ResultCode payResult = moneyService.pay(leader, serviceFee - 500);
        if (payResult == ResultCode.NOT_ENOUGH_MONEY) {
            return new CommonResponse(ResultCode.NOT_ENOUGH_MONEY); 
        } 

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

        inviteMembersInWaiting(party);
        return new CommonResponse();
    }

    private void inviteMembersInWaiting(Party party) {
        List<User> waitingUsers = waitingService.getTop3Waitings();
        for (User waitingUser : waitingUsers) {
            memberService.joinAfterPay(party, waitingUser);
            waitingService.deleteWaiting(waitingUser.getUserId());
        }
        refreshIsFull(party);
    }

    private void refreshIsFull(Party party) {
        int count = memberService.countMembers(party);
        if (count < 4) {
            party.setFull(false);
        } else {
            party.setFull(true);
        }
        partyRepo.save(party);
    }

    @Transactional
    public CommonResponse deleteParty(String userId, Integer partyId) {
        Optional<User> user = userRepo.findByUserId(userId);
        if (!user.isPresent()) {
            return CommonResponse(ResultCode.NOT_EXIST_USER);
        }
        User presentUser = user.get();
        PartyMember partyMember = memberRepo.findOneByUser(presentUser).get();
        
        if (!memberService.checkLeader(partyMember)) {
            return CommonResponse(ResultCode.LEADER_ONLY);
        }
        
        Party party = memberService.getParty(partyMember);
        if (!party.getPartyId().equals(partyId)) {
            return CommonResponse(ResultCode.IN_PARTY_ONLY);
        }

        memberService.refundByPartyDelete(party);
        memberRepo.deleteAllByParty(party);
        partyRepo.deleteById(partyId);
        return new CommonResponse();
    }
    
    private CommonResponse CommonResponse(ResultCode notExistUser) {
        return null;
    }

    @Transactional
    public CommonResponse getOutParty(String userId, Integer partyId) {
        Optional<User> user = userRepo.findByUserId(userId);

        if (!user.isPresent()) {
            return new CommonResponse(ResultCode.NOT_EXIST_USER);
        }

        User currentUser = user.get();
        PartyMember partyMember = memberRepo.findOneByUser(currentUser).get();

        if (memberService.checkLeader(partyMember)) {
            return new CommonResponse(ResultCode.PARTY_MEMBER_ONLY);
        }

        Party party = memberService.getParty(partyMember);

        if (!party.getPartyId().equals(partyId)) {
            return new CommonResponse(ResultCode.IN_PARTY_ONLY);
        }

        memberRepo.deleteById(partyMember.getPartyMemberId());
        moneyService.refund(currentUser.getUserId() , partyMember);
        party.setFull(false);
        partyRepo.save(party);

        return new CommonResponse();
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
            return new MyParty(ResultCode.ON_WAITING);
        }

        return new MyParty(ResultCode.HAS_NO_PARTY);
    }

    public CommonResponse updatePartyInfo(String userId, PartyUpdateDto info) {
        User user = userRepo.findByUserId(userId).get();
        PartyMember partyMember = memberRepo.findOneByUser(user).get();
        
        if (memberService.checkLeader(partyMember)) {
            return memberService.changeInfoOfLeader(partyMember, partyMember.getParty(), info);
        }
        
        return memberService.changeInfoOfMember(partyMember, partyMember.getParty(), info);
    }


    @Transactional
    public CommonResponse join(String userId) {
        User user = userRepo.findByUserId(userId).get();

        ResultCode payResult = moneyService.pay(user, serviceFee);
        if (payResult == ResultCode.NOT_ENOUGH_MONEY) {
            return new CommonResponse(ResultCode.NOT_ENOUGH_MONEY); 
        } 
        
        Optional<Party> anyNotFullParty = getAnyNotFullParty();
        if (anyNotFullParty.isEmpty()) {
            waitingService.addWaiting(user);
            return new CommonResponse(ResultCode.ON_WAITING); 
        }

        memberService.joinAfterPay(anyNotFullParty.get(), user);
        return new CommonResponse(); 
    }

    private Optional<Party> getAnyNotFullParty() {
        List<Party> parties = partyRepo.findNotFullOldestParties();
        if (parties.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(parties.get(0));
    }
}
