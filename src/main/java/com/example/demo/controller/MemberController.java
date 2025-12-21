package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Member signup(@RequestBody Member member){
        return memberService.signup(member);
    }

    @GetMapping
    public List<Member> getAllMembers(){
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable("id") Long id){
        try {
            Member member = memberService.findById(id);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            e.printStackTrace(); // 로그 확인
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member member){
        return memberService.update(id,member);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable("id") Long id){
        memberService.delete(id);
    }
}
