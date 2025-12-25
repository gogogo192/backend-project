package com.example.demo.Member.dto;

import com.example.demo.Member.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private String tier;

    // Entity를 DTO로 변환 (비밀번호는 제외!)
    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.tier = (member.getMembership() != null) ?
                member.getMembership().getTier().name() : "NONE";
    }
}