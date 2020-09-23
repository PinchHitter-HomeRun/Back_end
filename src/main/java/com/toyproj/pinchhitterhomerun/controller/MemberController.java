package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{memberId}")
    public String signUp(@PathVariable String memberId, String requestData) {
        Member newMember = new Member(

        );

        memberService.join(newMember);
        return memberId;
    }

    @GetMapping("/")
    public String test1(@RequestParam(value="msg") String msg) {
        return msg;
    }



}
