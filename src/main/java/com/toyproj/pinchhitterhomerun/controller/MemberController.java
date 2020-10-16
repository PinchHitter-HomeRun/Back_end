package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.Member;
import com.toyproj.pinchhitterhomerun.model.MemberJoin;
import com.toyproj.pinchhitterhomerun.model.PasswordHint;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/")
    public Map<String, Member> signUp(@RequestBody MemberJoin newMember) throws MemberException {

        Member member = newMember.getMember();

        if (!memberService.isAvailable(member.getLoginId())) {
            return null;
        }

        if (null != member.getBranch()) {
            memberService.requestToBranchMaster(member.getId(), member.getBranch().getId());
        }

        memberService.join(member, newMember.getHintId(), newMember.getAnswer());

        Map<String, Member> result = new HashMap<>();
        result.put("result", member);

        return result;
    }
    /*public TestResult signUp() {
        TestResult test = new TestResult("success");
        return test;
    }*/

    @PostMapping("/{loginId}")
    public Map<String, Member> signIn(@PathVariable String loginId, String passWord) throws MemberException {
        Map<String, Member> result = new HashMap<>();

        result.put("result", memberService.signIn(loginId, passWord));

        return result;
    }

    @GetMapping("/{memberId}")
    public Map<String, Member> getMemberInfo(@PathVariable Long memberId) {
        Map<String, Member> result = new HashMap<>();

        result.put("result", memberService.getMemberInfo(memberId));

        return result;
    }

    @DeleteMapping("/{memberId}")
    public Map<String, Member> leaveMember(@PathVariable Long memberId) {
        Map<String, Member> result = new HashMap<>();

        result.put("result", memberService.leave(memberId));

        return result;
    }

    @GetMapping("/{memberId}/hint")
    public Map<String, String> getMemberPasswordHint(@PathVariable Long memberId) {
        Map<String, String> result = new HashMap<>();

        result.put("result", memberService.getPasswordHint(memberId).getText());

        return result;
    }

    @PostMapping("{memberId}/answer")
    public Map<String, Boolean> isCorrectAnswer(@PathVariable Long memberId, @RequestParam(value = "answer") String answer) {
        Map<String, Boolean> result = new HashMap<>();

        result.put("result", answer.equals(memberService.getHintAnswer(memberId)));

        return result;
    }

    @PutMapping("{loginId}/password")
    public Map<String, Boolean> updateMemberPassword(@PathVariable String loginId, String passWord) {
        Map<String, Boolean> result = new HashMap<>();

        result.put("result", memberService.updatePassword(loginId, passWord) != null);

        return result;
    }

    @GetMapping("/hint")
    public Map<String, List<String>> getHintList() {
        List<String> hintList = new ArrayList<>();
        List<PasswordHint> passwordHintList = memberService.getAllHint();
        for (PasswordHint passwordHint : passwordHintList) {
            hintList.add(passwordHint.getText());
        }

        Map<String, List<String>> result = new HashMap<>();

        result.put("result", hintList);

        return result;
    }

    @ResponseBody
    @GetMapping("/")
    public String test1(@RequestParam(value = "msg")String msg) {
        return msg;
    }


}
