package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final MemberService memberService;

    // 생년월일로 아이디 찾기
    @ApiOperation("생년월일로 아이디 찾기")
    @GetMapping
    public ResponseResult<String> findLoginId(@RequestParam("birth_day") String birthDay, @RequestParam("name") String name) {
        final var findLoginId = memberService.findLoginId(name, birthDay);

        return new ResponseResult<>(findLoginId);
    }

    // 아이디 사용가능 여부 체크
    @ApiOperation("아이디 사용가능 여부 체크")
    @PostMapping("/{requestId}")
    public ResponseResult<Boolean> checkAvailableId(@PathVariable("requestId") String requestId) throws MemberException {
        final var result = memberService.isAvailable(requestId);

        return new ResponseResult<>(result);
    }

}
