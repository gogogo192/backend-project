package com.example.demo.dto;

import com.example.demo.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {
    private String email;
    private String password;
    private String name;

    // DTO를 Entity로 변환하는 편의 메서드
    public Member toEntity() {
        Member member = new Member();
        member.setEmail(this.email);
        member.setPassword(this.password);
        member.setName(this.name);
        return member;
    }
}

