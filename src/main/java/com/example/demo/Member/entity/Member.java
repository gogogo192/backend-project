package com.example.demo.Member.entity;

import com.example.demo.membership.entity.Membership; // 멤버십 엔티티 임포트
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    // 비즈니스 로직 연동: 1:1 멤버십 관계 추가
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    // 편의 메서드: 회원가입 시 기본 멤버십(BRONZE)을 자동으로 생성해주기 위함
    public void assignDefaultMembership(Membership membership) {
        this.membership = membership;
    }
}