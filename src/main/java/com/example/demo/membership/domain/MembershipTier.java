package com.example.demo.membership.domain;

import lombok.Getter;
@Getter
public enum MembershipTier {
    VIP(0.15, 1000000, 6),   // 15% 할인, 누적 100만 원, 6개월 유지 시
    GOLD(0.10, 500000, 3),    // 10% 할인, 누적 50만 원, 3개월 유지 시
    SILVER(0.05, 100000, 0),  // 5% 할인, 누적 10만 원, 바로 적용
    BRONZE(0.0, 0, 0);        // 할인 없음

    private final double discountRate; // 할인율
    private final int requiredAmount;  // 필요 구매 금액
    private final int maintenanceMonth; // 최소 유지 기간(개월)

    MembershipTier(double discountRate, int requiredAmount, int maintenanceMonth) {
        this.discountRate = discountRate;
        this.requiredAmount = requiredAmount;
        this.maintenanceMonth = maintenanceMonth;
    }
}