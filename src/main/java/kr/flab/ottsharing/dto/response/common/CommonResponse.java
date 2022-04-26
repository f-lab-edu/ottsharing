package kr.flab.ottsharing.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    public ResultCode resultCode = ResultCode.SUCCESS;
}
