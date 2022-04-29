package kr.flab.ottsharing.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.dto.request.PartyUpdateDto;
import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import kr.flab.ottsharing.repository.PartyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyMemberService {
    private final PartyMemberRepository memberRepo;
    private final PartyRepository partyRepo;
    private final PartyMemberRepository partyMemberRepo;
    private final MoneyService moneyService;

    public Boolean checkLeader(PartyMember partyMember) {
        return partyMember.isLeader();
    }

    public Party getParty(PartyMember partyMember) {
        return partyMember.getParty();
    }

    public void joinAfterPay(Party party, User user) {
        PartyMember member = PartyMember.builder()
            .user(user)
            .nickname(user.getUserId())
            .isLeader(false)
            .party(party)
            .build();
        memberRepo.save(member);
        
        refreshIsFull(party);
    }

    private void refreshIsFull(Party party) {
        int count = countMembers(party);
        if (count < 4) {
            party.setFull(false);
        } else {
            party.setFull(true);
        }
        partyRepo.save(party);
    }

    public CommonResponse changeInfoOfLeader(PartyMember partyMember, Party party, PartyUpdateDto info) {
        if (info.checkAllBlank()) {
            return new CommonResponse(ResultCode.NOTHING_CHANGED);
        }

        if (info.existNickName()) {
            if (isDuplicatedNickname(party, info.getNicknameToChange())) {
                return new CommonResponse(ResultCode.DUPLICATED_NICKNAME);
            }
            partyMember.setNickname(info.getNicknameToChange());
            partyMemberRepo.save(partyMember);
        }

        if (info.existId()) {
            party.setOttId(info.getOttId());
        }

        if (info.existPassword()) {
            party.setOttPassword(info.getOttPassword());
        }

        if (info.existIdOrPassword()) {
            partyRepo.save(party);
        }
        
        return new CommonResponse();
    }

    public CommonResponse changeInfoOfMember(PartyMember partyMember, Party party, PartyUpdateDto info) {
        if (info.existIdOrPassword()) {
            return new CommonResponse(ResultCode.LEADER_ONLY);
        }

        if (!info.existNickName()) {
            return new CommonResponse(ResultCode.NOTHING_CHANGED);
        }

        if (isDuplicatedNickname(party, info.getNicknameToChange())) {
            return new CommonResponse(ResultCode.DUPLICATED_NICKNAME);
        }
        partyMember.setNickname(info.getNicknameToChange());
        partyMemberRepo.save(partyMember);
        
        return new CommonResponse();
    }

    public boolean isDuplicatedNickname(Party party, String nickname) {
        List<PartyMember> partyMembers = partyMemberRepo.findByParty(party);
        for (PartyMember member : partyMembers ) {
            if(member.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public List<PartyMember> getUsersPaidAt(int day) {
        if (day >= 28) {
            return memberRepo.findByCreatedDay28To31();
        }
        return memberRepo.findByCreatedDay1To28(day);
    }
  
    public int countMembers(Party party) {
        return memberRepo.findByParty(party).size();
    }

    @Transactional
    public void refundByPartyDelete(Party party) {
        List<PartyMember> partyMembers = partyMemberRepo.findByParty(party);
        for (PartyMember member : partyMembers) {
            String userId = member.getUser().getUserId();
            moneyService.refund(userId, member);
        }
    }
}
