package com.example.demo.Member.dto;

import com.example.demo.Member.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private String tier;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        // member 안에 membership이 있으면 tier를 가져오고, 없으면 "NONE"
        this.tier = (member.getMembership() != null) ?
                member.getMembership().getTier().name() : "NONE";
    }
}