package kr.flab.ottsharing.protocol;

import java.util.List;

import kr.flab.ottsharing.entity.User;
import lombok.Getter;

@Getter
public class MyParty {
    private Status status;
    private User leader;
    private List<PartyMember> partyMembers;
    private String ottId;
    private String ottPw;

    public MyParty(Status status) {
        this.status = status;
        this.partyMembers = null;
        this.ottId = null;
        this.ottPw = null;
    }

    public MyParty(User leader, List<User> partyMembers, String ottId, String ottPw) {
        this.status = Status.HAS_PARTY;
        this.leader = leader;
        this.ottId = ottId;
        this.ottPw = ottPw;
    }

    public enum Status { NOT_LOGIN, HAS_NO_PARTY, WAITING_FOR_PARTY, HAS_PARTY }
}
