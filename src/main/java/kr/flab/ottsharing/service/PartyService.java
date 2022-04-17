package kr.flab.ottsharing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.exception.WrongInfoException;
import kr.flab.ottsharing.protocol.MyParty;
import kr.flab.ottsharing.protocol.PartyCreateResult;
import kr.flab.ottsharing.protocol.PartyMemberInfo;
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
    private final PartyMemberService partymemberService;

    public PartyCreateResult create(User leader, String ottId, String ottPassword) {
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

        if (!partymemberService.checkLeader(partymember)) {
            throw new WrongInfoException("삭제 권한이 없습니다" + userId );
        }

        Party party = partymemberService.getParty(partymember);

        if (!party.getPartyId().equals(partyId)) {
            throw new WrongInfoException("삭제 권한의 그룹이 아닙니다" + partyId );
        }

        memberRepo.deleteAllByParty(party);
        partyRepo.deleteById(partyId);

        return "삭제 완료되었습니다";
    }

    public String getOutParty(String userId, Integer partyId) {
        Optional<User> user = userRepo.findByUserId(userId);
        if (!user.isPresent()) {
            throw new WrongInfoException("존재하지 않는 회원id를 입력했습니다" + userId );
        }
        User presentUser = user.get();
        PartyMember partymember = memberRepo.findOneByUser(presentUser).get();

        if (partymemberService.checkLeader(partymember)) {
            throw new WrongInfoException("리더인 경우, 탈퇴가 아닌 파티해체 절차로 가주세요" + userId );
        }

        Party party = partymemberService.getParty(partymember);

        if (!party.getPartyId().equals(partyId)) {
            throw new WrongInfoException("탈퇴 권한의 그룹이 아닙니다" + partyId );
        }

        memberRepo.deleteById(partymember.getPartyMemberId());

        return "파티 탈퇴 완료되었습니다";
    }

    public MyParty fetchMyParty(User user) {
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

    // Party Entity 구조 변경으로 인해 동작하지 않는 코드
    public Party enrollParty(String leaderId, String getottId, String getottPassword) {
        // User Repository 구조 개편으로 코드 정상적으로 동작하지 않음
        /*

        /*User teamleader = userRepo.getById(leaderId);
        Party party = Party.builder().leader(teamleader).ottId(getottId).ottPassword(getottPassword).build();
        Party enrolledParty = partyRepo.save(party);

        return enrolledParty; */
        return null;
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
