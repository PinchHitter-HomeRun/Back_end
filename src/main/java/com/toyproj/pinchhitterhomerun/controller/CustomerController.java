package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.model.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final MemberService memberService;

    // 생년월일로 아이디 찾기
    @ApiOperation("생년월일로 아이디 찾기")
    @PostMapping
    public ResponseResult<String> findLoginId(@RequestParam("birth_day") String birthDay, @RequestParam("name") String name) {
       String result = memberService.findLoginId(name,birthDay);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

    // 아이디 사용가능 여부 체크
    @ApiOperation("아이디 사용가능 여부 체크")
    @PostMapping("/{requestId}")
    public ResponseResult<Boolean> checkAvailableId(@PathVariable("reqeustId") String requestId) throws MemberException {
        boolean result = memberService.isAvailable(requestId);

        return new ResponseResult<>(ErrorMessage.SUCCESS, result);
    }

}
