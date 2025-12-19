package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public Member signup(Member member){
        if(memberRepository.findByEmail(member.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }
        return memberRepository.save(member);
    }

    public List<Member> findAll(){return memberRepository.findAll(); }

    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    }

    public Member update(Long id, Member updatedMember){
        Member member = findById(id);
            member.setEmail(updatedMember.getEmail());
        member.setName(updatedMember.getName());
        member.setPassword(updatedMember.getPassword());
        return memberRepository.save(member);
    }

    public void delete(Long id){
        Member member = findById(id);
        memberRepository.delete(member);
    }
}
