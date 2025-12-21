package com.example.demo.entity;

import jakarta.persistence.*; // 전체 임포트
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member") // 관례적으로 테이블명 명시
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
}