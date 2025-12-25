package com.example.demo.Member.service;

import com.example.demo.Member.dto.MemberRequestDto;
import com.example.demo.Member.dto.MemberResponseDto;
import com.example.demo.Member.entity.Member;
import com.example.demo.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 최적화
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional // 쓰기 작업은 따로 설정
    public MemberResponseDto signup(MemberRequestDto requestDto){
        if(memberRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }
        Member member = memberRepository.save(requestDto.toEntity());
        return new MemberResponseDto(member);
    }

    public List<MemberResponseDto> findAll(){
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    public MemberResponseDto findById(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        return new MemberResponseDto(member);
    }

    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto requestDto){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        member.setEmail(requestDto.getEmail());
        member.setName(requestDto.getName());
        member.setPassword(requestDto.getPassword());

        return new MemberResponseDto(member); // Dirty Checking으로 자동 저장됨
    }

    @Transactional
    public void delete(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        memberRepository.delete(member);
    }


}