package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.HintAnswerReq;
import com.toyproj.pinchhitterhomerun.response.MemberHintRes;
import com.toyproj.pinchhitterhomerun.service.MemberPasswordHintService;
import com.toyproj.pinchhitterhomerun.service.PasswordHintService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/hint")
public class MemberPasswordHintController {

    @Autowired
    MemberPasswordHintService memberPasswordHintService;

    @Autowired
    PasswordHintService passwordHintService;

    @ApiOperation("로그인 아이디와 생년월일로 사용자가 등록한 힌트 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginId", value = "사용자의 로그인 아이디", required = true, dataType = "String"),
            @ApiImplicitParam(name = "birthDay", value = "사용자의 생년월일 (yymmdd)", required = true, dataType = "String")
    })
    @ResponseBody
    @GetMapping
    public ResponseResult<MemberHintRes> getMemberHint(@RequestParam("loginId") String loginId,
                                                       @RequestParam("birthDay") String birthDay) {
        final var response = new MemberHintRes();

        final var memberHint = memberPasswordHintService.getPasswordHint(loginId, birthDay);

        if (!memberHint.isSuccess()) {
            return new ResponseResult<>(response).setResult(memberHint.getResult()).build();
        }

        final var hintText = passwordHintService.getHintText(memberHint.getResponse().getHint().getId());

        if (!hintText.isSuccess()) {
            return new ResponseResult<>(response).setResult(hintText.getResult()).build();
        }

        response.setId(memberHint.getResponse().getId());
        response.setHintText(hintText.getResponse());

        return new ResponseResult<>(response);
    }

    @ApiOperation("입력한 답변과 사용자 질문의 답변이 일치한지 여부")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberHintId", value = "사용자가 등록한 힌트 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "request", value = "질문의 답변이 담긴 request", required = true, dataType = "HintAnswerReq")
    })
    @ResponseBody
    @PostMapping("/{memberHintId}")
    public ResponseResult<Boolean> answerIsMatched(@PathVariable("memberHintId") Long memberHintId,
                                                   @RequestBody HintAnswerReq request) {
        final var result = memberPasswordHintService.matchHintAnswer(memberHintId, request.getAnswer());

        if (!result.isSuccess()) {
            return new ResponseResult<>(false).setResult(result.getResult()).build();
        }

        return new ResponseResult<>(result);
    }
}
