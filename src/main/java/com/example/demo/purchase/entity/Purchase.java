package com.example.demo.purchase.entity;

import com.example.demo.Member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// purchase/entity/Purchase.java
@Entity
@Getter
@NoArgsConstructor
public class Purchase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long amount; // 구매 금액
    private LocalDateTime purchaseDate;

    public Purchase(Member member, long amount) {
        this.member = member;
        this.amount = amount;
        this.purchaseDate = LocalDateTime.now();
    }
}