package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
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
    public Member getMember(@PathVariable Long id){
        return memberService.findById(id);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member member){
        return memberService.update(id,member);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id){
        memberService.delete(id);
    }
}
