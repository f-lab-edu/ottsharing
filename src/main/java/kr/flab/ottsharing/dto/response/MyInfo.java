package kr.flab.ottsharing.dto.response;

import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyInfo extends CommonResponse {
    String userId;
    String email;
    Long money;
}
