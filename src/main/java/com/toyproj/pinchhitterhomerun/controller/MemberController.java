package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.*;
import com.toyproj.pinchhitterhomerun.request.CheckPassWordReq;
import com.toyproj.pinchhitterhomerun.request.LoginReq;
import com.toyproj.pinchhitterhomerun.request.MemberJoinReq;
import com.toyproj.pinchhitterhomerun.request.UpdatePassWordReq;
import com.toyproj.pinchhitterhomerun.response.MemberRes;
import com.toyproj.pinchhitterhomerun.service.BranchRequestService;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import com.toyproj.pinchhitterhomerun.util.BeanToProtocol;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "회원가입 request", required = true, dataType = "MemberJoinReq")
    })
    @ResponseBody
    @PutMapping
    public ResponseResult<MemberRes> signUp(@RequestBody MemberJoinReq request) {
        final var response = new MemberRes();

        final var memberJoin = memberService.join(
                request.getLoginId(),
                request.getPassWord(),
                SnsType.fromInt(request.getSnsType()),
                request.getName(),
                request.getBirthDay(),
                SexType.fromInt(request.getSexType()),
                request.getPhone(),
                request.getBranchId(),
                request.getHintId(),
                request.getAnswer()
        );

        if (!memberJoin.isSuccess()) {
            return new ResponseResult<>(response).setResult(memberJoin.getResult()).build();
        }

        BeanToProtocol.copyPropertyMemberRes(memberJoin.getResponse(), response);

        return new ResponseResult<>(response);
    }

    @ApiOperation("로그인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "로그인 정보 request\n" +
                    "loginId : 로그인할 id (이메일 형식)\n" +
                    "password : 비밀번호 (sha256)",
                    required = true,
                    dataType = "LoginReq")
    })
    @ResponseBody
    @PostMapping
    public ResponseResult<MemberRes> signIn(@RequestBody LoginReq request) {
        final var response = new MemberRes();

        final var memberLogin = memberService.signIn(request.getLoginId(), request.getPassWord());

        if (!memberLogin.isSuccess()) {
            return new ResponseResult<>(response).setResult(memberLogin.getResult()).build();
        }

        BeanToProtocol.copyPropertyMemberRes(memberLogin.getResponse(), response);

        return new ResponseResult<>(response);
    }

    @ApiOperation("컬럼 id로 사용자 정보 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "사용자 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @GetMapping("/{memberId}")
    public ResponseResult<MemberRes> getMemberInfo(@PathVariable("memberId") Long memberId) {
        final var response = new MemberRes();

        final var memberInfo = memberService.getMemberInfo(memberId);

        if (!memberInfo.isSuccess()) {
            return new ResponseResult<>(response).setResult(memberInfo.getResult()).build();
        }

        BeanToProtocol.copyPropertyMemberRes(memberInfo.getResponse(), response);

        return new ResponseResult<>(response);
    }

    @ApiOperation("탈퇴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "사용자 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @DeleteMapping("/{memberId}")
    public ResponseResult<Void> leaveMember(@PathVariable("memberId") Long memberId) {
        final var result = memberService.leave(memberId);

        return new ResponseResult<>(result);
    }

    @ApiOperation("비밀번호가 맞는지 체크")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "사용자 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "request", value = "현재 비밀번호 request\n" +
                    "currentPassWord : 현재 비밀번호 (sha256)",
                    required = true,
                    dataType = "CheckPassWordReq")
    })
    @ResponseBody
    @PostMapping("{memberId}/checkpassword")
    public ResponseResult<Void> checkPassWord(@PathVariable("memberId") Long memberId,
                                              @RequestBody CheckPassWordReq request) {
        final var result = memberService.checkPassword(memberId, request.getCurrentPassWord());

        return new ResponseResult<>(result);
    }

    @ApiOperation("비밀번호 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "사용자 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "request", value = "변경할 비밀번호 request\n" +
                    "updatePassWord : 변경하고자하는 비밀번호 (sha256)",
                    required = true,
                    dataType = "UpdatePassWordReq")
    })
    @ResponseBody
    @PutMapping("{memberId}/password")
    public ResponseResult<MemberRes> updateMemberPassword(@PathVariable("memberId") Long memberId,
                                                          @RequestBody UpdatePassWordReq request) {
        // TODO : 비밀번호 체크를 했는지 확인할 필요가 있음
        final var response = new MemberRes();

        final var updateMember = memberService.updatePassword(memberId, request.getUpdatePassWord());

        if (!updateMember.isSuccess()) {
            return new ResponseResult<>(response).setResult(updateMember.getResult()).build();
        }

        BeanToProtocol.copyPropertyMemberRes(updateMember.getResponse(), response);

        return new ResponseResult<>(response);
    }

    @ApiOperation("사용자가 속한 지점 조회")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "사용자 ID", required = true, dataType = "long", example = "0")
    })
    @GetMapping("{memberId}/branch")
    public ResponseResult<Branch> getMemberBranch(@PathVariable("memberId") Long memberId) {
        final var result = memberService.getMemberBranch(memberId);

        return new ResponseResult<>(result);
    }

    @ApiOperation("사용자 지점에서 탈퇴")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "사용자 ID", required = true, dataType = "long", example = "0")
    })
    @PutMapping("{memberId}/branch/leave")
    public ResponseResult<MemberRes> leaveMemberBranch(@PathVariable("memberId") Long memberId) {
        final var response = new MemberRes();

        final var leaveBranch = memberService.leaveBranch(memberId);

        BeanToProtocol.copyPropertyMemberRes(leaveBranch.getResponse(), response);

        return new ResponseResult<>(response);
    }
}
