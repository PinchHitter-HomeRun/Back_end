package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.service.MemberService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final MemberService memberService;

    public CustomerController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{requestId}")
    public boolean checkAvailableId(@PathVariable String requestId) {
        return memberService.isAvailable(requestId);
    }
}
