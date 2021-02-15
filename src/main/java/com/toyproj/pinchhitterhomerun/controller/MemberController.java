package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.requestbean.LoginReq;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    BranchRequestService branchRequestService;

    @ApiOperation("회원가입")
    @PutMapping
    public ResponseResult<Member> signUp(@RequestBody MemberJoin newMember) throws MemberException {

        if (!memberService.isAvailable(newMember.getLoginId()).getResponse()) {
            return null;
        }

        final var result = memberService.join(newMember);

        if (!result.isSuccess()) {
            return new ResponseResult<>(result);
        }

        if (null != newMember.getBranchId()) {
            branchRequestService.requestToBranchMaster(result.getResponse().getId(), result.getResponse().getBranch().getId());
        }

        return new ResponseResult<>(result);
    }

    @ApiOperation("로그인")
    @PostMapping
    public ResponseResult<Member> signIn(LoginReq request) {
        final var result = memberService.signIn(request.getLoginId(), request.getPassWord());

        return new ResponseResult<>(result);
    }

    @ApiOperation("컬럼 id로 사용자 정보 검색")
    @GetMapping("/{memberId}")
    public ResponseResult<Member> getMemberInfo(@PathVariable Long memberId) {
        final var result = memberService.getMemberInfo(memberId);

        return new ResponseResult<>(result);
    }

    @ApiOperation("탈퇴")
    @DeleteMapping("/{memberId}")
    public ResponseResult<Member> leaveMember(@PathVariable Long memberId) {
        final var result = memberService.leave(memberId);

        return new ResponseResult<>(result);
    }

    @ApiOperation("비밀번호 변경")
    @PutMapping("{memberId}/password")
    public ResponseResult<Member> updateMemberPassword(@PathVariable("memberId") Long memberId,
                                                       @RequestParam("password") String passWord) {
        final var member = memberService.getMemberInfo(memberId);

        if (!member.isSuccess()) {
            return new ResponseResult<>(member);
        }

        final var checkPassword = memberService.checkPassword(member.getResponse().getId(), passWord);

        if (!checkPassword.isSuccess()) {
            return new ResponseResult<>(checkPassword);
        }

        final var result = memberService.updatePassword(member.getResponse().getId(), passWord);

        return new ResponseResult<>(result);
    }
    
    @ApiOperation("사용자가 속한 지점 조회")
    @GetMapping("{memberId}/branch")
    public ResponseResult<Branch> getMemberBranch(@PathVariable("memberId") Long memberId) {
        final var result = memberService.getMemberBranch(memberId);

        return new ResponseResult<>(result);
    }

    @ApiOperation("사용자 지점에서 탈퇴")
    @PutMapping("{memberId}/branch/leave")
    public ResponseResult<Member> leaveMemberBranch(@PathVariable("memberId") Long memberId) {
        final var result = memberService.leaveBranch(memberId);

        return new ResponseResult<>(result);
    }
}
