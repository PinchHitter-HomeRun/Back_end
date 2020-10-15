package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final MemberService memberService;

    public CustomerController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/")
    public Map<String, String> findLoginId(@RequestParam(value = "birth_day") String birthDay, @RequestParam(value = "name") String name) {
        Map<String, String> result = new HashMap<>();

        result.put("result", memberService.findLoginId(name,birthDay));

        return result;
    }

    @PostMapping("/{requestId}")
    public Map<String, Boolean> checkAvailableId(@PathVariable String requestId) throws MemberException {
        Map<String, Boolean> result = new HashMap<>();

        result.put("result", memberService.isAvailable(requestId));

        return result;
    }

}
