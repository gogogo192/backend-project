package com.example.demo.membership.entity;

import com.example.demo.Member.entity.Member; // Member 엔티티 임포트
import com.example.demo.membership.domain.MembershipTier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipTier tier = MembershipTier.BRONZE;

    private long totalPurchaseAmount = 0; // 누적 구매 금액

    private LocalDateTime lastTierChangedDate = LocalDateTime.now(); // 마지막 등급 변경일

    private int consecutiveMaintenanceMonths = 0; // 연속 유지 개월 수

    // 양방향 매핑이 필요한 경우 추가 (선택사항)
    @OneToOne(mappedBy = "membership")
    private Member member;

    /**
     * 등급 업데이트 로직 (비즈니스 핵심)
     */
    public void updateTier(long currentMonthPurchase) {
        this.totalPurchaseAmount += currentMonthPurchase;

        // 1. 금액에 따른 목표 등급 계산
        MembershipTier nextTier = determineTierByAmount(this.totalPurchaseAmount);

        // 2. 강등 방지 및 유지 로직
        if (this.tier.getRequiredAmount() > this.totalPurchaseAmount) {
            // 현재 유지 기간이 기준보다 적으면 강등시키지 않음
            if (this.consecutiveMaintenanceMonths < this.tier.getMaintenanceMonth()) {
                // 유지 (아무 작업 안 함)
            } else {
                this.tier = nextTier;
                this.lastTierChangedDate = LocalDateTime.now();
                this.consecutiveMaintenanceMonths = 0; // 강등 시 초기화
            }
        } else if (this.tier != nextTier) {
            // 승급 시
            this.tier = nextTier;
            this.lastTierChangedDate = LocalDateTime.now();
            this.consecutiveMaintenanceMonths = 0; // 승급 시 초기화
        }
    }

    /**
     * 금액별 등급 판별 로직
     */
    private MembershipTier determineTierByAmount(long amount) {
        if (amount >= 1000000) return MembershipTier.VIP;
        if (amount >= 500000) return MembershipTier.GOLD;
        if (amount >= 100000) return MembershipTier.SILVER;
        return MembershipTier.BRONZE;
    }

    /**
     * 할인 적용된 금액 계산
     */
    public int applyDiscount(int price) {
        return (int) (price * (1 - tier.getDiscountRate()));
    }

    /**
     * 유지 개월 수 증가 (Batch 작업 등에서 호출)
     */
    public void addMaintenanceMonth() {
        this.consecutiveMaintenanceMonths++;
    }
}