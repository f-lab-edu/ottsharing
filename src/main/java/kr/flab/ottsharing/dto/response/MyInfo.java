package kr.flab.ottsharing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyInfo {
    String userId;
    String email;
    Long money;
}
