package com.example.demo.Member.service;

import com.example.demo.Member.dto.MemberRequestDto;
import com.example.demo.Member.dto.MemberResponseDto;
import com.example.demo.Member.entity.Member;
import com.example.demo.Member.repository.MemberRepository;
import com.example.demo.membership.entity.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 로직
     */
    @Transactional
    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }

        Member member = requestDto.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // 기본 멤버십 생성 및 연결
        Membership defaultMembership = new Membership();
        member.assignDefaultMembership(defaultMembership);

        Member savedMember = memberRepository.save(member);

        // 이제 정석대로 객체만 넘겨도 BRONZE가 잘 나올 겁니다.
        return new MemberResponseDto(savedMember);
    }

    /**
     * 전체 회원 조회
     */
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 개별 회원 조회
     */
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        return new MemberResponseDto(member);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        member.setEmail(requestDto.getEmail());
        member.setName(requestDto.getName());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        member.setPassword(encodedPassword);

        return new MemberResponseDto(member);
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        memberRepository.delete(member);
    }

    public MemberResponseDto login(String email, String password) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));


        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


        return new MemberResponseDto(member);
    }

}