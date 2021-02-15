package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.requestbean.HintAnswerReq;
import com.toyproj.pinchhitterhomerun.responsebean.MemberHintAns;
import com.toyproj.pinchhitterhomerun.service.MemberPasswordHintService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/hint")
public class MemberPasswordHintController {

    @Autowired
    MemberPasswordHintService memberPasswordHintService;

    @ApiOperation("로그인 아이디와 생년월일로 사용자가 등록한 힌트 조회")
    @GetMapping
    public ResponseResult<MemberHintAns> getMemberHint(@RequestParam("loginId") String loginId,
                                                       @RequestParam("birthDay") String birthDay) {
        final var result = memberPasswordHintService.getPasswordHint(loginId, birthDay);

        return new ResponseResult<>(result);
    }

    @ApiOperation("입력한 답변과 사용자 질문의 답변이 일치한지 여부")
    @PutMapping("/{memberHintId}")
    public ResponseResult<Boolean> answerIsMatched(@PathVariable("memberHintId") Long memberHintId,
                                                   @RequestBody HintAnswerReq request) {
        final var result = memberPasswordHintService.matchHintAnswer(memberHintId, request.getAnswer());

        return new ResponseResult<>(result);
    }
}
