package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.*;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final BranchRequestService branchRequestService;

    // 회원가입
    @PostMapping("/")
    public Map<String, Member> signUp(@RequestBody MemberJoin newMember) throws MemberException {

        if (!memberService.isAvailable(newMember.getLoginId())) {
            return null;
        }

        Member joinedMember = memberService.join(newMember);

        if (null != newMember.getBranchId()) {
            BranchRequest request = new BranchRequest(joinedMember.getId(), joinedMember.getBranch().getId());
            branchRequestService.requestToBranchMaster(request);
        }

        Map<String, Member> result = new HashMap<>();
        result.put("result", joinedMember);

        return result;
    }
    /*public TestResult signUp() {
        TestResult test = new TestResult("success");
        return test;
    }*/

    // 로그인
    @PostMapping("/{loginId}")
    public Map<String, Member> signIn(@PathVariable String loginId, String passWord) throws MemberException {
        Map<String, Member> result = new HashMap<>();

        result.put("result", memberService.signIn(loginId, passWord));

        return result;
    }

    // 사용자 정보 가져오기
    @GetMapping("/{memberId}")
    public Map<String, Member> getMemberInfo(@PathVariable Long memberId) {
        Map<String, Member> result = new HashMap<>();

        result.put("result", memberService.getMemberInfo(memberId));

        return result;
    }

    // 탈퇴
    @DeleteMapping("/{memberId}")
    public Map<String, Member> leaveMember(@PathVariable Long memberId) {
        Map<String, Member> result = new HashMap<>();

        result.put("result", memberService.leave(memberId));

        return result;
    }

    // 패스워드 분실 질문 가져오기
    @GetMapping("/{memberId}/hint")
    public Map<String, String> getMemberPasswordHint(@PathVariable Long memberId) {
        Map<String, String> result = new HashMap<>();

        result.put("result", memberService.getPasswordHint(memberId).getText());

        return result;
    }

    // 답변이 일치하는지 판단
    @PostMapping("{memberId}/answer")
    public Map<String, Boolean> isCorrectAnswer(@PathVariable Long memberId, @RequestParam(value = "answer") String answer) {
        Map<String, Boolean> result = new HashMap<>();

        result.put("result", memberService.matchHintAnswer(memberId, answer));

        return result;
    }

    // 비밀번호 수정
    @PutMapping("{loginId}/password")
    public Map<String, Boolean> updateMemberPassword(@PathVariable String loginId, String passWord) {
        Map<String, Boolean> result = new HashMap<>();

        result.put("result", memberService.updatePassword(loginId, passWord) != null);

        return result;
    }

    // 모든 질문 가져오기
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

    // 지점에 속한 사용자들 가져오기
    @GetMapping("/branch/{branchId}")
    public Map<String, List<Member>> getBranchMembers(@PathVariable Long branchId) {
        Map<String, List<Member>> result = new HashMap<>();

        result.put("result", memberService.getBranchMembers(branchId));

        return result;
    }



    @ResponseBody
    @GetMapping("/")
    public String test1(@RequestParam(value = "msg")String msg) {
        return msg;
    }


}
