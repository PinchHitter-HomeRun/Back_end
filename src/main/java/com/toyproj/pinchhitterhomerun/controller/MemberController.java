package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.*;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("회원가입")
    @PostMapping
    public ResponseResult<Member> signUp(@RequestBody MemberJoin newMember) throws MemberException {

        if (!memberService.isAvailable(newMember.getLoginId())) {
            return null;
        }

        Member result = memberService.join(newMember);

        if (null != newMember.getBranchId()) {
            BranchRequest request = new BranchRequest(result.getId(), result.getBranch().getId());
            branchRequestService.requestToBranchMaster(request);
        }

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 로그인
    @ApiOperation("로그인")
    @PostMapping("/{loginId}")
    public ResponseResult<Member> signIn(@PathVariable String loginId, String passWord) throws MemberException {
        Member result = memberService.signIn(loginId, passWord);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 사용자 정보 가져오기
    @ApiOperation("컬럼 id로 사용자 정보 검색")
    @GetMapping("/{memberId}")
    public ResponseResult<Member> getMemberInfo(@PathVariable Long memberId) {
        Member result = memberService.getMemberInfo(memberId);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 탈퇴
    @ApiOperation("탈퇴")
    @DeleteMapping("/{memberId}")
    public ResponseResult<Member> leaveMember(@PathVariable Long memberId) {
        Member result = memberService.leave(memberId);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 패스워드 분실 질문 가져오기
    @ApiOperation("패스워드 분실 질문")
    @GetMapping("/{memberId}/hint")
    public ResponseResult<String> getMemberPasswordHint(@PathVariable Long memberId) {
        String result = memberService.getPasswordHint(memberId).getText();

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 답변이 일치하는지 판단
    @ApiOperation("질문 답변 일치 여부")
    @PostMapping("{memberId}/answer")
    public ResponseResult<Boolean> isCorrectAnswer(@PathVariable("memberId") Long memberId,
                                                   @RequestParam("answer") String answer) {
        boolean result = memberService.matchHintAnswer(memberId, answer);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 비밀번호 수정
    @ApiOperation("비밀번호 변경")
    @PutMapping("{loginId}/password")
    public ResponseResult<Boolean> updateMemberPassword(@PathVariable("loginId") String loginId,
                                                        @RequestParam("password")String passWord) {
        boolean result = memberService.updatePassword(loginId, passWord) != null;

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 모든 질문 가져오기
    @ApiOperation("설정 가능한 모든 질문 리스트")
    @GetMapping("/hint")
    public ResponseResult<List<String>> getHintList() {
        List<String> result = new ArrayList<>();
        List<PasswordHint> passwordHintList = memberService.getAllHint();
        for (PasswordHint passwordHint : passwordHintList) {
            result.add(passwordHint.getText());
        }

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 멤버가 속한 지점 가져오기
    @ApiOperation("회원이 속한 지점 검색")
    @GetMapping("/member/{memberId}")
    public ResponseResult<Branch> getMemberBranch(@PathVariable("memberId") Long memberId) {
        Branch result = memberService.getMemberBranch(memberId);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    @ApiOperation("테스트 입니다.")
    @GetMapping
    public ResponseResult<String> test1(@RequestParam("msg")String msg) {
        return new ResponseResult<>(ErrorMessage.SUCCESS, msg);
    }
}
