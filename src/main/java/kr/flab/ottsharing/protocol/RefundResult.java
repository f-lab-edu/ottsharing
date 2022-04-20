package kr.flab.ottsharing.protocol;

import lombok.Getter;

@Getter
public class RefundResult {
    
    private Status status;
    private Long refundMoney;

    public RefundResult(Long refundMoney){
        this.status = Status.SUCCESS;
        this.refundMoney = refundMoney;
    }

    public enum Status { SUCCESS }
}
