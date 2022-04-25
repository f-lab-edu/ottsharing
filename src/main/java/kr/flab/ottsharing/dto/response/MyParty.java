package kr.flab.ottsharing.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class MyParty {
    private Status status;
    private List<PartyMemberInfo> memberInfos;
    private String ottId;
    private String ottPw;

    public MyParty(Status status) {
        this.status = status;
        this.memberInfos = null;
        this.ottId = null;
        this.ottPw = null;
    }

    public MyParty(List<PartyMemberInfo> memberInfos, String ottId, String ottPw) {
        this.status = Status.HAS_PARTY;
        this.memberInfos = memberInfos;
        this.ottId = ottId;
        this.ottPw = ottPw;
    }

    public enum Status { HAS_NO_PARTY, WAITING_FOR_PARTY, HAS_PARTY }
}
