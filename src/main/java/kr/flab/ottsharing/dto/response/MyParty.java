package kr.flab.ottsharing.dto.response;

import java.util.List;

import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import lombok.Getter;

@Getter
public class MyParty extends CommonResponse{
    private List<PartyMemberInfo> memberInfos;
    private String ottId;
    private String ottPw;

    public MyParty(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.memberInfos = null;
        this.ottId = null;
        this.ottPw = null;
    }

    public MyParty(List<PartyMemberInfo> memberInfos, String ottId, String ottPw) {
        resultCode = ResultCode.SUCCESS;
        this.memberInfos = memberInfos;
        this.ottId = ottId;
        this.ottPw = ottPw;
    }
}
