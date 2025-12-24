package com.example.demo.dto;

import com.example.demo.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;

    // Entity를 DTO로 변환 (비밀번호는 제외!)
    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
    }
}