package com.toyproj.pinchhitterhomerun.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.MemberJoin;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/")
        public Member signUp(@RequestBody MemberJoin newMember) {

        Member member = newMember.getMember();

        if(!memberService.isAvailable(member.getLoginId())) {
            throw new IllegalStateException("이미 가입한 회원입니다.");
        }

        memberService.join(member, newMember.getHintId(),newMember.getAnswer());

        return member;
    }

    @PostMapping("/{loginId}")
    public Member signIn(@PathVariable String loginId, String passWord) {
        return memberService.signIn(loginId, passWord);
    }

    @GetMapping("/{memberId}")
    public Member getMemberInfo(@PathVariable Long memberId) {
        return memberService.getMemberInfo(memberId);
    }

    @DeleteMapping("{memberId}")
    public Member leaveMember(@PathVariable Long memberId) {
        return memberService.leave(memberId);
    }

    @GetMapping("{memberId}/hint")
    public String getMemberPasswordHint(@PathVariable Long memberId) {
        return memberService.getPasswordHint(memberId).getText();
    }

    @PostMapping("{memberId}/answer")
    public Boolean isCorrectAnswer(@PathVariable Long memberId, @RequestParam(value = "answer") String answer) {
        return answer.equals(memberService.getHintAnswer(memberId));
    }

    @PutMapping("{loginId}/password")
    public Boolean updateMemberPassword(@PathVariable String loginId, String passWord) {
        return memberService.updatePassword(loginId,passWord) != null;
    }

    @GetMapping("/")
    public String test1(@RequestParam(value="msg") String msg) {
        return msg;
    }


}
