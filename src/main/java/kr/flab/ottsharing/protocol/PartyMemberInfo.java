package kr.flab.ottsharing.protocol;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PartyMemberInfo {
    private String userId;
    private String nickname;
    private boolean isLeader;
}