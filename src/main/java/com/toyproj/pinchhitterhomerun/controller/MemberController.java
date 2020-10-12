package com.toyproj.pinchhitterhomerun.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.MemberJoin;
import com.toyproj.pinchhitterhomerun.model.PasswordHint;
import com.toyproj.pinchhitterhomerun.model.TestResult;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/")
    public Member signUp(@RequestBody MemberJoin newMember) throws MemberException {

        Member member = newMember.getMember();

        if (!memberService.isAvailable(member.getLoginId())) {
            return null;
        }

        memberService.join(member, newMember.getHintId(), newMember.getAnswer());

        return member;
    }
    /*public TestResult signUp() {
        TestResult test = new TestResult("success");
        return test;
    }*/

    @PostMapping("/{loginId}")
    public Member signIn(@PathVariable String loginId, String passWord) throws MemberException {
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
        return memberService.updatePassword(loginId, passWord) != null;
    }

    @GetMapping("hint")
    public List<String> getHintList() {
        List<String> result = new ArrayList<>();
        List<PasswordHint> passwordHintList = memberService.getAllHint();
        for (PasswordHint passwordHint : passwordHintList) {
            result.add(passwordHint.getText());
        }

        return result;
    }

    @GetMapping("/")
    public String test1(@RequestParam(value = "msg") String msg) {
        return msg;
    }


}
