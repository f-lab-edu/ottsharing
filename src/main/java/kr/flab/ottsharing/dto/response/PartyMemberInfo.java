package kr.flab.ottsharing.dto.response;

import kr.flab.ottsharing.dto.response.common.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PartyMemberInfo extends CommonResponse {
    public String memberId;
    public String nickname;
    public boolean isLeader;
}
