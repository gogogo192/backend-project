package com.example.demo.membership.service;

import com.example.demo.Member.entity.Member;
import com.example.demo.Member.repository.MemberRepository;
import com.example.demo.purchase.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MemberRepository memberRepository;
    private final PurchaseRepository purchaseRepository; // 추가

    @Transactional
    public void refreshAllMemberships() {
        List<Member> members = memberRepository.findAll();
        LocalDateTime firstDayOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);

        for (Member member : members) {
            long monthlyAmount = getMonthlyPurchase(member, firstDayOfMonth);
            member.getMembership().updateTier(monthlyAmount);
        }
    }

    // private 메서드로 구현하여 에러 해결!
    private long getMonthlyPurchase(Member member, LocalDateTime startDate) {
        Long sum = purchaseRepository.sumAmountByMemberAndPurchaseDateAfter(member, startDate);
        return (sum != null) ? sum : 0L; // 구매 내역이 없으면 0원
    }
}