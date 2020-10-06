package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final MemberService memberService;

    public CustomerController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/")
    public String findLoginId(@RequestParam(value = "birth_day") String birthDay, @RequestParam(value = "name") String name) {
        return memberService.findLoginId(name,birthDay);
    }

    @PostMapping("/{requestId}")
    public boolean checkAvailableId(@PathVariable String requestId) {
        return memberService.isAvailable(requestId);
    }

}
