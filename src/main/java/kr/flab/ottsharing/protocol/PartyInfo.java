package kr.flab.ottsharing.protocol;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PartyInfo {
    private String ottId;
    private String ottPassword;
    private List<PartyMemberInfo> members;
    private LocalDateTime created;
}