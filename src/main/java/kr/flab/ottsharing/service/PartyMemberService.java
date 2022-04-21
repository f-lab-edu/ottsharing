package kr.flab.ottsharing.service;

import java.util.List;

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
    private final PartyService partyService;
    private final PartyMemberRepository memberRepo;
    private final PartyRepository partyRepo;
    private final PartyMemberRepository partyMemberRepo;

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
        
        partyService.refreshIsFull(party);
    }

    public String changeInfoOfLeader(PartyMember partyMember, Party party, UpdatePartyInfo info) {
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
            throw new WrongInfoException("바꿀 정보가 아무것도 입력되지 않았습니다.");
        }
        
        if (hasNickname) {
            checkNickNameDuplicate(party, nickname);
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
        
        return "리더의 요청으로 파티 정보 수정 완료되었습니다.";
    }

    public String changeInfoOfMember(PartyMember partyMember, Party party, UpdatePartyInfo info) {
        String nickname = info.getNicknameToChange();
        String ottId = info.getOttId();
        String ottPassword = info.getOttPassword();

        if (ottId != null || ottPassword != null) {
            throw new WrongInfoException("팀원은 파티의 아이디 또는 패스워드를 수정할 수 없습니다. ");
        }

        if (nickname == null) {
            throw new WrongInfoException("바꿀 닉네임을 적어줘야 합니다." + nickname);
        }

        checkNickNameDuplicate(party, nickname);
        partyMember.setNickname(nickname);
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
}
