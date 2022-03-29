package kr.flab.ottsharing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.protocol.PartyMemberInfo;
import kr.flab.ottsharing.repository.PartyMemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyMemberService {
    private final PartyMemberRepository memberRepo;

    public List<PartyMemberInfo> getPartyMemberInfos(Party party) {
        List<PartyMemberInfo> memberInfos = new ArrayList<>();

        List<PartyMember> memberEntities = memberRepo.findByParty(party);
        for (PartyMember memberEntity : memberEntities) {
            User user = memberEntity.getUser();
            PartyMemberInfo memberInfo = PartyMemberInfo.builder()
                .userId(user.getUserId())
                .nickname(memberEntity.getNickname())
                .isLeader(memberEntity.isLeader())
                .build();

            memberInfos.add(memberInfo);
        }

        return memberInfos;
    }
}