package com.example.demo.controller;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
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
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers(){
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable("id") Long id){
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long id, @RequestBody MemberRequestDto requestDto){
        return ResponseEntity.ok(memberService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id){
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}