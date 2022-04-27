package kr.flab.ottsharing.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.exception.WrongInfoException;
import kr.flab.ottsharing.protocol.UpdatePartyInfo;
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

    public String changeInfoOfLeader(PartyMember partyMember, Party party, UpdatePartyInfo info) {
        
        if (info.existNickName()) {
            checkNickNameDuplicate(party, info.getNicknameToChange());
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
        
        return "리더의 요청으로 파티 정보 수정 완료되었습니다.";
    }

    public String changeInfoOfMember(PartyMember partyMember, Party party, UpdatePartyInfo info) {

        if (info.existIdOrPassword()) {
            throw new WrongInfoException("팀원은 파티의 아이디 또는 패스워드를 수정할 수 없습니다.");
        }

        if (!info.existNickName()) {
            throw new WrongInfoException("바꿀 닉네임을 적어줘야 합니다." + info.getNicknameToChange());
        }

        checkNickNameDuplicate(party, info.getNicknameToChange());
        partyMember.setNickname(info.getNicknameToChange());
        partyMemberRepo.save(partyMember);
        
        return "닉네임 수정 완료되었습니다.";
    }

    public void checkNickNameDuplicate(Party party, String nickname) {
        List<PartyMember> partyMembers = partyMemberRepo.findByParty(party);
        for (PartyMember member : partyMembers ) {
            if(member.getNickname().equals(nickname)) {
                throw new WrongInfoException("파티 내에 같은 닉네임이 있습니다." + nickname );
            }
        }
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
