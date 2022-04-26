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
        boolean hasNickname = false;
        boolean hasOttId = false;
        boolean hasOttPassword = false;
        boolean saveParty = false;
        String nickname = info.getNicknameToChange();
        String ottId = info.getOttId();
        String ottPassword = info.getOttPassword();

        if (info.getNicknameToChange() != null) {
            hasNickname = true;
        }

        if (info.getOttId() != null) {
            hasOttId = true;
        }

        if (info.getOttPassword() != null) {
            hasOttPassword = true;
        }

        if (!hasNickname && !hasOttId && !hasOttPassword) {
            return new CommonResponse(ResultCode.NOTHING_CHANGED);
        }
        
        if (hasNickname) {
            if (isDuplicatedNickname(party, nickname)) {
                return new CommonResponse(ResultCode.DUPLICATED_NICKNAME);
            }
            partyMember.setNickname(nickname);
            partyMemberRepo.save(partyMember);
        }

        if (hasOttId) {
            party.setOttId(ottId);
            saveParty = true;
        }

        if (hasOttPassword) {
            party.setOttPassword(ottPassword);
            saveParty = true;
        }

        if(saveParty == true) {
            partyRepo.save(party);
        }
        
        return new CommonResponse();
    }

    public CommonResponse changeInfoOfMember(PartyMember partyMember, Party party, PartyUpdateDto info) {
        String nickname = info.getNicknameToChange();
        String ottId = info.getOttId();
        String ottPassword = info.getOttPassword();

        if (ottId != null || ottPassword != null) {
            return new CommonResponse(ResultCode.LEADER_ONLY);
        }

        if (nickname == null) {
            return new CommonResponse(ResultCode.NOTHING_CHANGED);
        }

        if (isDuplicatedNickname(party, nickname)) {
            return new CommonResponse(ResultCode.DUPLICATED_NICKNAME);
        }
        partyMember.setNickname(nickname);
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
