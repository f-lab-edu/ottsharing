package kr.flab.ottsharing.dto.response;

import kr.flab.ottsharing.dto.response.common.CommonResponse;
import kr.flab.ottsharing.dto.response.common.ResultCode;
import lombok.Getter;

@Getter
public class RefundResult extends CommonResponse {
    private Long refundMoney;

    public RefundResult(Long refundMoney) {
        resultCode = ResultCode.SUCCESS;
        this.refundMoney = refundMoney;
    }
    
    public RefundResult(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
