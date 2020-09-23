package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class MemberController {

    @GetMapping("/{userId}")
    public String test(@PathVariable String userId) {
        return userId;
    }

    @PostMapping("/")
    public String test1(@RequestParam(value="msg") String msg) {
        return msg;
    }



}
