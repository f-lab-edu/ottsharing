package kr.flab.ottsharing.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PartyMemberInfo {
    public String memberId;
    public String nickname;
    public boolean isLeader;
}
