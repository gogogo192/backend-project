package com.example.demo.purchase.repository;

import com.example.demo.Member.entity.Member;
import com.example.demo.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // 특정 회원의 특정 기간 내 구매 금액 합계 조회
    @Query("SELECT SUM(p.amount) FROM Purchase p WHERE p.member = :member AND p.purchaseDate >= :startDate")
    Long sumAmountByMemberAndPurchaseDateAfter(Member member, LocalDateTime startDate);
}