package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.service.MemberService;
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
    @PostMapping("/")
    public Map<String, String> findLoginId(@RequestParam(value = "birth_day") String birthDay, @RequestParam(value = "name") String name) {
        Map<String, String> result = new HashMap<>();

        result.put("result", memberService.findLoginId(name,birthDay));

        return result;
    }

    // 아이디 사용가능 여부 체크
    @PostMapping("/{requestId}")
    public Map<String, Boolean> checkAvailableId(@PathVariable String requestId) throws MemberException {
        Map<String, Boolean> result = new HashMap<>();

        result.put("result", memberService.isAvailable(requestId));

        return result;
    }

}
