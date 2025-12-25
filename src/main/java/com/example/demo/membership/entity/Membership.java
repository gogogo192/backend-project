package com.example.demo.membership.entity;

import com.example.demo.membership.domain.MembershipTier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// membership/entity/Membership.java
@Entity
@Getter
@NoArgsConstructor
public class Membership {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MembershipTier tier = MembershipTier.BRONZE;

    private long totalPurchaseAmount = 0; // 누적 구매 금액
    private LocalDateTime lastTierChangedDate; // 마지막 등급 변경일
    private int consecutiveMaintenanceMonths; // 연속 유지 개월 수

    // 등급 업데이트 로직 (비즈니스 핵심)
    public void updateTier(long currentMonthPurchase) {
        this.totalPurchaseAmount += currentMonthPurchase;

        // 1. 승급 조건 체크
        MembershipTier nextTier = determineTierByAmount(this.totalPurchaseAmount);

        // 2. 강등 및 유지 로직
        if (this.tier.getRequiredAmount() > this.totalPurchaseAmount) {
            // 유지 기간 확인 후 미달 시 강등
            if (this.consecutiveMaintenanceMonths < this.tier.getMaintenanceMonth()) {
                this.tier = nextTier;
                this.lastTierChangedDate = LocalDateTime.now();
            }
        } else {
            this.tier = nextTier; // 조건 만족 시 유지 혹은 승급
        }
    }

    private MembershipTier determineTierByAmount(long amount) {
        if (amount >= 1000000) return MembershipTier.VIP;
        if (amount >= 500000) return MembershipTier.GOLD;
        if (amount >= 100000) return MembershipTier.SILVER;
        return MembershipTier.BRONZE;
    }

    // 할인 적용된 금액 계산
    public int applyDiscount(int price) {
        return (int) (price * (1 - tier.getDiscountRate()));
    }
}