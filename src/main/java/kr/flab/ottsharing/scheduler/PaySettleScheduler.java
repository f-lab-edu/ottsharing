package kr.flab.ottsharing.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.flab.ottsharing.dto.response.common.ResultCode;
import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;
import kr.flab.ottsharing.service.MoneyService;
import kr.flab.ottsharing.service.PartyMemberService;
import kr.flab.ottsharing.service.PartyService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaySettleScheduler {
    private final PartyMemberService memberService;
    private final MoneyService moneyService;
    private final PartyService partyService;

    @Scheduled(cron = "0 0 6 * * *")
	public void payAndSettleEverySixMorning() throws Exception {
        List<PartyMember> members = getMembersAtPaySettleDay();

        for (PartyMember member : members) {
            boolean thisMonth = member.getCreatedTime().getMonth() == LocalDateTime.now().getMonth();
            if (thisMonth) continue;
            
            User user = member.getUser();
            Party party = member.getParty();

            if (member.isLeader()) {
                moneyService.settle(user, 15000);
                continue;
            }

            ResultCode payResult = moneyService.pay(user, 5000);
            if (payResult == ResultCode.NOT_ENOUGH_MONEY) {
                partyService.getOutParty(user.getUserId(), party.getPartyId());
            }
        }
    }

    private List<PartyMember> getMembersAtPaySettleDay() {
        int day = LocalDateTime.now().getDayOfMonth();
        if (day > 28) {
            return new ArrayList<>();
        }

        if (day == 28) {
            return memberService.getUsersPaidAt(28);
        }
        return memberService.getUsersPaidAt(day);
    }
}
