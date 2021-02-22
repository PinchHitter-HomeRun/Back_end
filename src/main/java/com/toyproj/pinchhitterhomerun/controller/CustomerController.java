package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    MemberService memberService;

    @ApiOperation("생년월일로 아이디 찾기")
    @GetMapping
    public ResponseResult<String> findLoginId(@RequestParam("name") String name, @RequestParam("birth_day") String birthDay) {
        final var findLoginId = memberService.findLoginId(name, birthDay);

        return new ResponseResult<>(findLoginId);
    }

    @ApiOperation("아이디 사용가능 여부 체크")
    @PostMapping("/{requestId}")
    public ResponseResult<Boolean> checkAvailableId(@PathVariable("requestId") String requestId) {
        final var result = memberService.isAvailable(requestId);

        return new ResponseResult<>(result);
    }

}
